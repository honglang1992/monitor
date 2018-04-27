package com.vrv.monitor.core.zabbix;

import com.zabbix4j.ZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.item.ItemGetRequest;
import com.zabbix4j.item.ItemGetResponse;
import com.zabbix4j.item.ItemObject;
import com.zabbix4j.trigger.TriggerGetRequest;
import com.zabbix4j.trigger.TriggerGetResponse;
import com.zabbix4j.trigger.TriggerObject;
import org.apache.commons.lang.StringUtils;
import com.vrv.monitor.core.zabbix.annotation.ZabbixFieldDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ZabbixApi 代理类
 * Created by Dendi on 2017/7/31.
 */
public class ZabbixApiProxy {

    private final static Logger logger = LoggerFactory.getLogger(ZabbixApiProxy.class);

    private ZabbixApi zabbixApi;

    private String username;

    private String password;

    public ZabbixApiProxy(ZabbixApi zabbixApi, String username, String password) {
        this.zabbixApi = zabbixApi;
        this.username = username;
        this.password = password;
        login();
    }

    /**
     * 当前zabbixApi的 auth是否有效
     */
    private Boolean authValid = false;

    public ZabbixApi getZabbixApi() {
        return zabbixApi;
    }

    public void setZabbixApi(ZabbixApi zabbixApi) {
        this.zabbixApi = zabbixApi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthValid() {
        return authValid;
    }

    public void setAuthValid(Boolean authValid) {
        this.authValid = authValid;
    }

    public boolean login() {
        if (!authValid) {//当前auth不可用
            try {
                zabbixApi.login(username, password);
                authValid = true;
                return true;
            } catch (ZabbixApiException e) {
                logger.error("login failed ",e);
                return false;//login报错，则返回false
            }
        } else {
            return true;
        }
    }

    public void reLogin() {
        try {
            zabbixApi.login(username, password);
            authValid = true;
        } catch (ZabbixApiException e) {
            logger.error("reLogin failed ",e);
        }
    }

    /**
     * 获取所有的监控项
     *
     * @param hostid
     * @return
     */
    public List<ItemGetResponse.Result> getAllItems(Integer hostid) {
        boolean redo;
        int redoTimes = 1; //session过期时,重新登陆
        List<ItemGetResponse.Result> items = new ArrayList<>();
        ItemGetRequest request = new ItemGetRequest();
        ItemGetRequest.Params params = request.getParams();
        List<Integer> hostIds = new ArrayList<Integer>();
        do {
            hostIds.add(hostid);
            params.setHostids(hostIds);
            request.setParams(params);
            try {
                ItemGetResponse response = zabbixApi.item().get(request);
                items = response.getResult();
                redo = false;
            } catch (Exception e) {
                /**
                 * 如果异常是：1.用户口令无效 2.session 过期，则需要重新登陆(限制一次)
                 */
                if ((!authValid || e.getMessage().contains("Session terminated")) && redoTimes-- > 0) {
                    reLogin();
                    redo = true;
                } else {
                    logger.error("zabbixApi failed ",e);
                    redo = false;
                }
            }
        } while (redo);
        return items;
    }

    /**
     * 获取所有的 触发器
     *
     * @param hostid
     * @return
     */
    public List<TriggerGetResponse.Result> getAllTriggers(Integer hostid) {
        List<TriggerGetResponse.Result> triggers = new ArrayList<>();
        TriggerGetRequest request = new TriggerGetRequest();
        TriggerGetRequest.Params params = request.getParams();
        List<Integer> hostids = new ArrayList<Integer>();
        hostids.add(hostid);
        params.setHostids(hostids);
        request.setParams(params);
        try {
            TriggerGetResponse response = zabbixApi.trigger().get(request);
            List<TriggerGetResponse.Result> result = response.getResult();
            triggers = result;
        } catch (Exception e) {
            logger.error("getAllTriggers failed ",e);
        }
        return triggers;
    }

    /**
     * com.vrv.monitor.core.zabbix-api获取的数据 转换成指定Model
     *
     * @param items
     * @param triggers
     * @param model
     */
    public <T> void converToModel(List<ItemGetResponse.Result> items, List<TriggerGetResponse.Result> triggers, T model) {
        Class clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            ZabbixFieldDescription annotation = field.getAnnotation(ZabbixFieldDescription.class);
            if (annotation != null && StringUtils.isNotBlank(annotation.key())) {
                /**
                 * item 映射
                 */
                for (ItemObject item : items) {
                    if (annotation.key().equalsIgnoreCase(item.getKey_())) {
                        try {
                            String value = item.getLastvalue();
                            if (field.getType() == Integer.class) {
                                field.set(model, Integer.valueOf(value));
                            } else if (field.getType() == Float.class) {
                                Float f = Float.valueOf(value);
                                f = (float) (Math.round(f * 100)) / 100;
                                field.set(model, f);
                            } else if (field.getType() == String.class) {
                                field.set(model, value);
                            } else if (field.getType() == Boolean.class) {
                                field.set(model, "1".equals(value) ? true : false);
                            }
                        } catch (Exception e) {
                            logger.error(field.getName() + "获取数据异常",e);
                        }
                        break;
                    }
                }
            } else if (annotation != null && StringUtils.isNotBlank(annotation.triggerKey()) && field.getType() == Boolean.class) {
                /**
                 * trigger 映射
                 */
                for (TriggerObject trigger : triggers) {
                    if (annotation.triggerKey().equalsIgnoreCase(trigger.getDescription())) {
                        try {
                            Integer value = trigger.getValue();
                            if (value == 0) {//表示trigger没有触发，这里我们用来表示监控的服务没有问题
                                field.set(model, true);
                            } else {
                                field.set(model, false);
                            }
                        } catch (Exception e) {
                            logger.error(field.getName() + "获取数据异常",e);
                        }
                        break;
                    }
                }
            }
        }
    }
}
