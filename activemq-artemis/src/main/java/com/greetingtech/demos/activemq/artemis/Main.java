package com.greetingtech.demos.activemq.artemis;

import org.apache.activemq.artemis.api.core.client.*;

public class Main {

    private static final String ADDRESS = "exampleAddress";

    private static final String QUEUE_NAME = "exampleQueue";

    public static void main(String[] args) throws Exception {

        ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616");

        Producer producer = new Producer(locator, ADDRESS, QUEUE_NAME);
        Consumer consumer = new Consumer(locator, QUEUE_NAME);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("demo finished");
    }
}

