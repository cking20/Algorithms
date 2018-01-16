package assignment1;
/**
 *
 * @author chrisrk192
 */

public class HashMap{
    private String URL;
    private long TimeDownloaded;
    public String FromDate,ToDate;
    public int StateID;
    private int size;
    private int count;
    private int current;
    public ListADT[] map;
     
    
    //Constructor
    public HashMap(){
        size = 32;
        count = 0;
        current = 0;
         //map = new LinkedList[size];
         //map  = new Element[size];
         map = new ListADT[size];
        //for(int i = 0; i < size; i++){
        //    map[i] = new ListADT();
        //}
    }
     //Constructor
    public HashMap(int s){
        size = s;
        count = 0;
        current = 0;
        //map = new LinkedList[size];
        //map = new Element[s];
       map = new ListADT[s];
       //for(int i = 0; i < size; i++){
       //     map[i] = new ListADT();
       // }
        
    }
   
    
    /**
     * S Fold hash the key into an index with max size of SIZE. Reasons for 
     *      implementing: taking 4 at a time allows for a larger range of
     *      indexes to be produced.
     * 
     * @param key the String to be hashed 
     * 
     * @return the hash map index
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
    /** 
     * @return count
     */
    public int getCount(){
        return count;
    }
    /**
     * @return size
     */
    public int getSize(){
        return size;
    }
    /**
     * @return current
     */
    public int getCurrent(){
        return current;
    }
    
    /**
     * Resets all iterators in the lists and map to their starting position
     */
    public void Reset(){
        current = 0;
        for(int i = 0; i < size; i++){
            if(map[i] != null)
                map[i].Reset();
        }
    }
    
    /**
     * Moves current to the next non null index
     * 
     * @return a non null index, or an index of SIZE if at the end
     */    
    private int findNextIndex(){
        //returns SIZE if at the end of the list, wont increment
        //else current will be at the next non null index.
        if (current <= size -1){
            current++;
            while (current < size -1 && map[current] == null) {            
                current++;
            }
            if(current == size)
                return current;
            if (map[current]== null) {//must be at end
                return current++;//size
            }
            return current;
        } else{
            return size;
        }
        
    }
    
    /**
     * Moves current along the indexes, will traverse through every element in 
     *      every list one element at a time if run repeatedly. 
     *     
     * ASSUMES Lists are reset before attempting to find the next element
     * @return true if more elements to traverse, false if at the end of the map
     */
    public boolean traverse(){
        //step through every item of every list
        //assuming all lists are reset
        
        if(current <= size -1){
            if(map[current] != null){
                //map[current].GetNext();
                if(!map[current].AtEnd() && !map[current].IsEmpty()){
                    return true;
                }else{
                    findNextIndex();
                    if (current >= size) {
                        return false;
                    }
                    return true;
                }
            } else{//current is null
                findNextIndex();
                if (current >= size) {
                        return false;
                    }
                return true;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Deep copy the Value of the current element 
     * @return the Value of the element at the current index of the map and the current index in the list
     */
    public Value Retrieve(){
        return map[current].Retrieve();
    }
      
    /**
     * Adds Value v in the list at the hashed index, creates a new list if the 
     *      index is null. If the list is 3/4 full it will double the size of
     *      the map and rehash the elements to a new map
     * @param v the value to add
     */
    public void add(Value v){
        count++;
        int index = hash(v.getKeyString());
        v.setHash(index);
        //instanciate a new List if attempting to add at null location
        if(map[index] == null) 
            map[index] = new ListADT();
        map[index].Add(v);
        
        if(count > size - (size >> 2)){
            //double map
            int oldsize = size;
            size = size << 1;
            
            ListADT[] newMap = new ListADT[size];
            for(int i = 0;i < oldsize; i++){
                if(map[i] != null){
                    map[i].Reset();//bring the itteratior back to 0
                    while(!map[i].AtEnd()){
                        int newIndex = hash(map[i].Retrieve().getKeyString());
                        if(newMap[newIndex] == null) newMap[newIndex] = new ListADT();
                        newMap[newIndex].Add(map[i].Retrieve().Clone());
                        map[i].GetNext();
                    }
                }
            }
            
            map = newMap;
        }
    }
    
    /**
     * Finds element that matches key and removes it from the list
     *      On multiple elements, it deletes the first found
     * @param key the unique identifier to be deleted
     */
    public void remove(String key){
        count--;
        int index = hash(key);
        map[index].Delete(key);
    }
    
    /**
     * Finds element that matches key
     * @param key the unique identifier to be found
     * @return true if found, false otherwise
     */
    public boolean contains(String key){
        int index = hash(key);
        return map[index].Search(key);
    }
    
    /**
     * Finds element that matches key and returns the value
     * @param key the unique identifier to be found
     * @return value if found, null otherwise
     */
    public Value get(String key){
        int index = hash(key);
        map[index].Search(key);
        return map[index].Retrieve();
    }
    
    /**
     * Traverses through the entire map and all lists to construct a string that
     *      resembles the number of elements per index.
     * @return a string resembling the structure of the map
     */
    public String PrintMap(){
        char symbol = '@';
        char whiteSpace = ' ';
        String printString = "";
        
        for(int i = 0; i < map.length; i++){
            printString = printString + i;
            if(map[i] != null){
                map[i].Reset();
                while (!map[i].AtEnd()) {                
                    printString = printString + whiteSpace + symbol;
                    map[i].GetNext();
                }
            }
            printString = printString + "\n";
        }
    return printString;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * @return the TimeDownloaded
     */
    public long getTimeDownloaded() {
        return TimeDownloaded;
    }

    /**
     * @param TimeDownloaded the TimeDownloaded to set
     */
    public void setTimeDownloaded(long TimeDownloaded) {
        this.TimeDownloaded = TimeDownloaded;
    }
   
    
    
}
    