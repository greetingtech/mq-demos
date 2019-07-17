package com.greetingtech.demos.kafka;

public class KafkaMain {

    public static void main(String[] args) throws Exception {

        MyProducer producer = new MyProducer();
        MyConsumer consumer = new MyConsumer();

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

    }

}
