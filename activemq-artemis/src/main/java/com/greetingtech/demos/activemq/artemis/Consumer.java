package com.greetingtech.demos.activemq.artemis;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.*;

public class Consumer extends Thread {

    private ServerLocator locator;

    private String queueName;

    public Consumer(ServerLocator locator, String queueName) {
        this.locator = locator;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        try {
            ClientSessionFactory factory = locator.createSessionFactory();
            ClientSession session = factory.createSession();
            session.start();
            ClientConsumer consumer = session.createConsumer(new SimpleString(queueName));
            for (;;) {
                ClientMessage receive = consumer.receive(5000);
                if (receive == null) {
                    System.out.println("timeout");
                    break;
                }
                System.out.println("Messages from Producer: " + receive.getBodyBuffer().readString());
                receive.acknowledge();
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
