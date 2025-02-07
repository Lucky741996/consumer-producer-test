package com.test.producer_consumer_app;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ProducerConsumerApp {
	private static final Logger logger =  LogManager.getLogger(ProducerConsumerApp.class);
    public static void main(String[] args) throws InterruptedException {
    	BasicConfigurator.configure();  
        MessageQueue queue = new MessageQueue(10); 
        String[] messages = {"Message 1", "Message 2", "errorMessage 3", "Message 4", "errorMessage 5"};

      
        Producer producer = new Producer(queue, messages);
        Consumer consumer = new Consumer(queue, messages.length);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        logger.info("Total successful messages: "+ consumer.getSuccessCount());
        logger.info("Total failed messages: "+ consumer.getErrorCount());
    }
}
