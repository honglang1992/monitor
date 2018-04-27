package com.vrv.monitor.datapicker.service.common;

import com.vrv.monitor.core.activemq.ActiveMQConnectionListener;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Dendi on 2017/10/19.
 */
public class ActiveMQConnectionListenerImpl extends ActiveMQConnectionListener {

    /**
     * @param url
     * @param logKey
     */
    public ActiveMQConnectionListenerImpl(String url, String logKey) {
        super(url, logKey);
    }

    @Override
    public void initConsumerAndProducer() throws JMSException {
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(logKey);
        producer = session.createProducer(destination);
    }

    @Override
    public void sendMsg(String msg) throws JMSException {
        TextMessage textMessage = session.createTextMessage(msg);
        producer.send(textMessage);
    }
}
