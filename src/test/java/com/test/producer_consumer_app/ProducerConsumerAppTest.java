package com.test.producer_consumer_app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProducerConsumerAppTest {

    private MessageQueue queue;
    private Consumer consumer;

    @BeforeEach
    public void setUp() {
        queue = new MessageQueue(10);  
    }

    
 // Test case with all successful messages
    @Test
    public void testSuccessScenario() throws InterruptedException {
        String[] messages = {"Message 1", "Message 2", "Message 3"};
        Producer producer = new Producer(queue, messages);
        consumer = new Consumer(queue, messages.length); 
        producer.start();
        consumer.start();

        producer.join(); 
        consumer.join();

        System.out.println("Test case: All successful messages");
        consumer.printStatistics();

        assertEquals(3, consumer.getSuccessCount()); 
        assertEquals(0, consumer.getErrorCount());  
    }

    
    // Test case with some failed messages
    @Test
    public void testFailureScenario() throws InterruptedException {
        String[] messages = {"Message 1", "errorMessage 2", "Message 3"};
        Producer producer = new Producer(queue, messages);
        consumer = new Consumer(queue, messages.length);
        producer.start();
        consumer.start();

        producer.join();  
        consumer.join(); 

        System.out.println("Test case: Some failed messages");
        consumer.printStatistics();

        assertEquals(2, consumer.getSuccessCount());
        assertEquals(1, consumer.getErrorCount());   
    }

    
    // Test case with no messages (empty queue scenario)
    @Test
    public void testEmptyQueueScenario() throws InterruptedException {
       
        String[] messages = {};
        Producer producer = new Producer(queue, messages);
        consumer = new Consumer(queue, 0); 
        producer.start();
        consumer.start();

        producer.join(); 
        consumer.join();  

        System.out.println("Test case: Empty queue scenario");
        consumer.printStatistics();

        assertEquals(0, consumer.getSuccessCount());  
        assertEquals(0, consumer.getErrorCount());   
    }
}



