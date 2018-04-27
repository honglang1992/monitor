package com.vrv.monitor.core.activemq;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.IOException;

public abstract class ActiveMQConnectionListener implements TransportListener {

    protected final static Logger logger = LoggerFactory.getLogger(ActiveMQConnectionListener.class);

    protected String logKey;

    protected TaskRunnerFactory reconnectTaskFactory;
    /**
     * 链接工厂
     */
    protected ActiveMQConnectionFactory connectionFactory;

    /**
     * MQ链接
     */
    protected ActiveMQConnection connection = null;

    /**
     * 会话
     */
    protected Session session = null;

    protected MessageConsumer consumer;

    protected MessageProducer producer;

    /**
     * 对消息传输命令进行监控
     *
     * @param arg0
     */
    @Override
    public void onCommand(Object arg0) {
    }

    /**
     * 对监控到的异常进行触发
     *
     * @param arg0
     */
    @Override
    public void onException(IOException arg0) {

        //重新连接时关闭注意关闭资源
        doClose();

        //重新链接
        doConnect(reconnectTaskFactory, connectionFactory);
    }

    /**
     * 当failover时触发
     */
    @Override
    public void transportInterupted() {
    }

    /**
     * 监控到failover恢复后进行触发
     */
    @Override
    public void transportResumed() {
    }

    /**
     * @param url
     */
    public ActiveMQConnectionListener(String url ,String logKey) {
        this.logKey=logKey;
        reconnectTaskFactory = new TaskRunnerFactory();
        reconnectTaskFactory.setDaemon(false); // to set daemon=false by kimmking
        reconnectTaskFactory.init();

        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, url);

        //链接并添加监听
        doConnect(reconnectTaskFactory, connectionFactory);
    }

    /**
     * 链接MQ
     *
     * @param reconnectTaskFactory
     * @param connectionFactory
     */
    protected void doConnect(TaskRunnerFactory reconnectTaskFactory, ActiveMQConnectionFactory connectionFactory) {
        while (true) {
            try {
                //从工厂中获取一个连接
                connection = (ActiveMQConnection) connectionFactory.createConnection();

                connection.setSessionTaskRunner(reconnectTaskFactory);

                connection.addTransportListener(this);

                connection.start();

                initConsumerAndProducer();

                logger.info("创建消息队连接成功:" + connectionFactory.getBrokerURL());
                break;
            } catch (JMSException e) {
                doClose();
                logger.error("创建消息队连接失败" + connectionFactory.getBrokerURL(), e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                    logger.error("创建消息队连接失败后 进行线程休眠 失败:", e1);
                }
            }
        }
    }

    //关闭连接
    protected void doClose() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("关闭连接失败", e);
        }
    }

    public abstract void initConsumerAndProducer() throws JMSException;

    public abstract void sendMsg(String msg) throws JMSException;
}
