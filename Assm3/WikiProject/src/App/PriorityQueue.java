/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.util.ArrayList;

/**
 * Prioritizes smaller double values
 * @author chrisrk192
 */
public class PriorityQueue {
    private int size;
    public int count;
    private ArrayList<Edge> queue;
    
    public PriorityQueue(int cap) {
        queue = new ArrayList<>();
        size = cap;
        count = 0;
    }
    
    /**
     * Adds e to the queue in it's appropriate location
     * @param e 
     */
    public void Push(Edge e){
        //if the queue is full, remove the last item, to make room for a more heavy weight
        if(count == size){
            queue.remove(size-1);
            count--;
        }
        int index = 0;
        while(index < count && e.weight > queue.get(index).weight){
            index++;
        }
        //System.out.println("adding at index" +index+ " with count "+count);
        queue.add(index, e);
        count++;    
    }
    
    /**
     * Returns the item with the highest priority
     * @return 
     */
    public Edge Pull(){
        if(count <= 0)
            return null;
        count--;
        return queue.remove(0);

    }
    
    /**
     * Overwrites the current queue and makes the list e into the used queue
     * @param e 
     */
    public void Load(ArrayList<Edge> e){
        queue = e;     
    } 
}
