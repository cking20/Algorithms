/*
 */
package assignment1;
import java.util.LinkedList;
import java.util.Collection;
/**
 *
 * @author chrisrk192
 */
public class HashMap {
    private int size;
    private int count;
    //private Element[] map;
    //private Collection<Element>[] map;
    
    private LinkedList<Element>[] map;
     
    
    //Constructor
    public HashMap(){
        size = 15;
        count = 0;
        //map = new Collection[size];
         map = new LinkedList[size];
         for(int i = 0; i < size; i++){
            map[i] = new LinkedList();
        }
    }
     //Constructor
    public HashMap(int s){
        size = s;
        count = 0;
        //map = new Collection[size];
        map = new LinkedList[size];
        for(int i = 0; i < size; i++){
            map[i] = new LinkedList();
        }
        
    }
    
    //transform the key into a hash
    public int hash(int key){
        int h = 0;
        //String s = Integer.toString(key);
        //for(int i = 0; i < s.length(); i++){
        while(key != 0){
            h += key & (size -1);
            key = key >> 4;
            //h = (h * s.charAt(i) ) & (size - 1);
           // System.err.println("h = " + h);
        }
        h &= (size -1);
        
        //h ^= size;
        //h = key ^ (key << 1);
        //h = h % (size -1);
        return h;
    }
    
    
    
    
    
    public void add(Element e){
        int index = hash(e.getKey());
        e.setHash(index);
        map[index].add(e);
        
        if(count > size - (size >> 2)){
            //double map
            System.out.println("DOUBLEING////////////////////////////////////////////////");
            //HashMap newMap = new HashMap(size << 1);
            
            
            
        }
        
    }
    
    public void remove(int key){
        int index = hash(key);
        Element e = new Element();
        e.setKey(key);
        map[index].remove(e); 
    }
    
    public boolean contains(int key){
        int index = hash(key);
        Element e = new Element();
        e.setKey(key);
        return map[index].contains(e); 
    }
    
    public String PrintMap(){
        String printString = "";
        for(int i = 0; i < map.length; i++){
            printString = printString + i;
            for(int c = 0; c < map[i].size(); c++){
                Element[] column = new Element[map[i].size() + 1];
                map[i].toArray(column);
                printString = printString + ". ";//+ column[c].getKey() + " "; //+ ". ";
            }
            printString = printString + "\n";
        }
    return printString;
    }
   
    
    
}
    