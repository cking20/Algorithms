/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loader;

import assignment1.HashMap;

/**
 *
 * @author chrisrk192
 */
public class CasheMap {
    private int size = 32;//chache size
    private int rows = 16;
    private int count;
    private int current;
    public HashMap[][] map;
    
    public CasheMap() {
        count = 0;
        map = new HashMap[size][rows];
    }
    
    /**
     * Checks attempts to add the data to the map, if its full, it evicts the LRU
     * @param hm 
     */
    public void Add(HashMap hm){
        int hash = hash(hm.getURL());
        int i;
        for(i = 0; i < rows; i++){
            if(map[hash][i] == null){
                map[hash][i] = hm;
            }
        }
        if(i == rows){//row full, evict LRU
            int row = Evict(hash);
            map[hash][row] = hm;
        }
    }
    
    /**
     * Locates the hashmap with the passed url
     * @param url
     * @return the hashmap, or null if it doesnt have it
     */
    public HashMap Search(String url){
        int hash = hash(url);
        for(int i = 0; i < rows; i++){
            if(map[hash][i] != null){
                if(map[hash][i].getURL().compareTo(url) == 0)
                    return map[hash][i];
            }
        }
        return null;
    }
    
    /**
     * Locates the oldest value in a given column
     * @param column
     * @return 
     */
    private int FindLRU(int column){
        long oldestDate = Long.MAX_VALUE;
        int oldestIndex = 0;
        
        for(int i = 0; i < rows; i ++){
            if(map[column][i] == null){
                return i;
            }
            if(map[column][i].getTimeDownloaded() < oldestDate){
                oldestDate = map[column][i].getTimeDownloaded();
                oldestIndex = i;
            }
        }
        return oldestIndex;
    }
    
    /**
     * Makes room in a given column
     * @param column
     * @return the row of the freed space
     */
    public int Evict(int column){
        int row = FindLRU(column);
        map[column][row] = null;
        return row;
    }
    /**
     * Transform the url into an index
     * @param key
     * @return 
     */
    public int hash(String key){
        //s fold hash
        int intLength = key.length() / 4;//how many folds
        long sum = 0;
        
        for (int j = 0; j < intLength; j++) {
          char c[] = key.substring(j * 4, (j * 4) + 4).toCharArray();
          long mult = 1;
          for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 197;//was 256, changed to prime number
          }
        }
        char c[] = key.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
          sum += c[k] * mult;
          mult *= 197;//was 256, changed to prime number
        }
        // & size would provide more speed, but less range
        return (int) (Math.abs(sum) % size); 
    }
}
