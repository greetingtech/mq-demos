package com.greetingtech.demos.activemq.artemis;

import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.*;

import java.util.Date;

public class Producer extends Thread {

    private ServerLocator locator;

    private String address;

    private String queueName;

    public Producer(ServerLocator locator, String address, String queueName) {
        this.locator = locator;
        this.address = address;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        try {
            ClientSessionFactory factory = locator.createSessionFactory();
            ClientSession session = factory.createSession();
            session.start();
            ClientProducer producer = session.createProducer(address);
            ClientSession.QueueQuery exampleQueue = session.queueQuery(new SimpleString(queueName));

            if (!exampleQueue.isExists()) {
                session.createQueue(address, RoutingType.ANYCAST, queueName, true);
            }

            for (int i = 0; i < 10; ++i) {
                ClientMessage message = session.createMessage(true);
                message.getBodyBuffer().writeString("Hello World at " + new Date().toString());
                producer.send(message);
                Thread.sleep(1000);
            }

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
