package com.test.producer_consumer_app;

import java.util.LinkedList;

public class MessageQueue {
    private final LinkedList<String> queue = new LinkedList<>();
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String message) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();  
        }
        queue.addLast(message);
        notifyAll();  
    }

    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();  
        }
        String message = queue.removeFirst();
        notifyAll();  
        return message;
    }
}

