/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 *
 * @author 5928036
 */
public class BoundedQueue<T> {
    private ArrayDeque<T> container = new ArrayDeque<>();
    private int capacity;

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }
    
    public void insert(T value) {
        if (isFull()) {
            container.poll();
        }
        
        container.offer(value);
    }
    
    public boolean isFull() {
        return container.size() >= capacity;
    }
    
    public int getSize() {
        return container.size();
    }
    
    public List<T> asList() {
        return container.stream().collect(Collectors.toList());
    }
}
