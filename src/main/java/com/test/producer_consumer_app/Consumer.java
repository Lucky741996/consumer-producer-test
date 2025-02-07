package com.test.producer_consumer_app;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Consumer extends Thread {
	private static final Logger logger =  LogManager.getLogger(Consumer.class);
    private final MessageQueue queue;
    private int successCount = 0;
    private int errorCount = 0;
    private final int totalMessagesToProcess;

    public Consumer(MessageQueue queue, int totalMessagesToProcess) {
        this.queue = queue;
        this.totalMessagesToProcess = totalMessagesToProcess;
    }

    @Override
    public void run() {
        int messagesProcessed = 0;

        while (messagesProcessed < totalMessagesToProcess) {
            try {
                String message = queue.consume();
                if (message != null) {
                    if (processMessage(message)) {
                        successCount++;
                    } else {
                        errorCount++;   
                    }
                    messagesProcessed++;
                }
            } catch (InterruptedException e) {
            	  logger.error("Consumer thread was interrupted", e);
                e.printStackTrace();
            }
        }

        printStatistics();
    }

    private boolean processMessage(String message) {
        if (message.contains("error")) {
        	logger.error("Error processing message: "+ message); 
            return false;
        }
        logger.info("Successfully processed message: "+ message); 
        return true;
    }

    public void printStatistics() {
    	  logger.info("\nTotal successful messages: "+ successCount);
          logger.info("Total failed messages: "+ errorCount);
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }
}

