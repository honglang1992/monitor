package com.vrv.monitor.datapicker.controller;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by Dendi on 2017/10/20.
 */
public class JettySpringMVCStart {
    // web访问的根路径http://ip:port/，相当于项目名,/即忽略项目名
    public static final String CONTEXT = "/";

    private static final String DEFAULT_WEBAPP_PATH = "webapp";

    public static Server createServerIn(int port) {
        // 创建Server
        Server server = new Server();
        // 添加ThreadPool
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();
        queuedThreadPool.setName("queuedTreadPool");
        queuedThreadPool.setMinThreads(10);
        queuedThreadPool.setMaxThreads(200);
        server.setThreadPool(queuedThreadPool);
        // 添加Connector
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        connector.setAcceptors(4);// 同时监听read事件的线程数
        connector.setMaxBuffers(2048);
        connector.setMaxIdleTime(10000);
        server.addConnector(connector);

        WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, CONTEXT);
        webContext.setDescriptor(DEFAULT_WEBAPP_PATH+"/WEB-INF/web.xml");
        webContext.setResourceBase(DEFAULT_WEBAPP_PATH);
        webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webContext);
        return server;
    }
}
