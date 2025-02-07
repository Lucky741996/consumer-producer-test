package com.test.producer_consumer_app;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

class Producer extends Thread {
	 private static final Logger logger =  LogManager.getLogger(Producer.class);
    private final MessageQueue queue;
    private final String[] messages;

    public Producer(MessageQueue queue, String[] messages) {
        this.queue = queue;
        this.messages = messages;
    }

    @Override
    public void run() {
        for (String message : messages) {
            try {
            	logger.info("Produced message:"+ message);
                queue.produce(message);
                Thread.sleep(500);  
            } catch (InterruptedException e) {
            	logger.error("Producer thread was interrupted", e);
                e.printStackTrace();
            }
        }
    }
}
