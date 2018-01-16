/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.peristance;

import java.io.Serializable;

/**
 * This class keeps track of file indexes
 * @author chrisrk192
 */
public class BTree  implements Serializable{
    private static final int MAXKEYS = 32; //= 2t -1
    private static final long NIL = -1;
    BNode theRoot;
    
    public BTree(){
        BNode n = new BNode();
        
        n.isLeaf = true;
        n.numkeys = 0;
        theRoot = n;
    }
    
    /**
     * Searches through the passed in tree for the passed url
     * @param aRoot tree to be searched
     * @param key the URL to be found
     * @returns file position if saved in BTree, -1 if not
     */
    public long Search(BNode aRoot, String key){
        int i = 0;
        //System.out.println("Searching for :"+key);
        while (i < aRoot.numkeys && key.compareTo(aRoot.keys[i]) > 0) {
            i++;
            //System.out.println("Loader.BTree.Search() i="+i);
            //System.out.println("Loader.BTree.Search()numK="+aRoot.numkeys);
        }
        if(i < aRoot.numkeys && key.compareTo( aRoot.keys[i]) == 0){
            //System.out.println("Found url:"+aRoot.keys[i]);
            return aRoot.values[i];
        }
        else if(aRoot.isLeaf || aRoot.children[i] == null)
            return -1;
        else{
            return Search(aRoot.children[i], key);
        }  
    }
    
    /**
     * Finds the url in the Btree and replaces the file index stored there
     * @param aRoot
     * @param key
     * @param value
     * @return 
     */
    public boolean Replace(BNode aRoot, String key, long value){
        int i = 0;
        //System.out.println("Searching for :"+key);
        while (i < aRoot.numkeys && key.compareTo( aRoot.keys[i]) > 0) {
            i++;
            //System.out.println("Loader.BTree.Search() i="+i);
            //System.out.println("Loader.BTree.Search()numK="+aRoot.numkeys);
        }
        if(i < aRoot.numkeys && key.compareTo( aRoot.keys[i]) == 0){
            //System.out.println("Found url:"+aRoot.keys[i]);
            //return aRoot.values[i];
            aRoot.values[i] = value;
            return true;
        }
        else if(aRoot.isLeaf || aRoot.children[i] == null)
            return false;
        else{
            return Replace(aRoot.children[i], key, value);
        }  
    }
    
    /**
     * Deletes key from aRoot
     * @param aRoot
     * @param key
     * @return true on found and deleted, false otherwise
     */
    public boolean Remove(BNode aRoot, String key){
        int i = 0;
        //System.out.println("Searching for :"+key);
        while (i < aRoot.numkeys && key.compareTo( aRoot.keys[i]) > 0) {
            i++;
            //System.out.println("Loader.BTree.Search() i="+i);
            //System.out.println("Loader.BTree.Search()numK="+aRoot.numkeys);
        }
        if(i < aRoot.numkeys && key.compareTo( aRoot.keys[i]) == 0){
            //System.out.println("Found url:"+aRoot.keys[i]);
            //real removeing work
            int j;
            for(j = i; j < aRoot.numkeys-1; j++){
                aRoot.keys[j] = aRoot.keys[j+1];
                aRoot.values[j] = aRoot.values[j+1];
            }
            aRoot.keys[j] = null;
            aRoot.values[j] = -1;
            aRoot.numkeys--;  
            return true;
        }
        else if(aRoot.isLeaf)
            return false;
        else{
            return Remove(aRoot.children[i], key);
        }
    }
    
    /**
     * Splits a full node into 2 half full nodes
     * @param x
     * @param i 
     */
    private void SplitChild(BNode x, int i){
        BNode z = new BNode();
        
        BNode y = x.children[i];
        z.isLeaf = y.isLeaf;
        
        int t = MAXKEYS/2;
        
        
        z.numkeys = t - 1;
        for(int j = 0; j < t - 1; j++){
            z.keys[j] = y.keys[j+t];
            z.values[j] = y.values[j+t];
        }
        if(!y.isLeaf){ 
            for(int j = 0; j < t;j++){
                z.children[j] = y.children[j+t];
            }
        }
        y.numkeys = t-1;
        for(int j = x.numkeys; j > i+1; j--){
            x.children[j+1] = x.children[j];
        }
        x.children[i+1] = z;
        for(int j = x.numkeys; j >= i; j--){
            
            x.keys[j+1] = x.keys[j];
            x.values[j+1] = x.values[j];
        }
        x.keys[i] = y.keys[t];
        x.values[i] = y.values[t];
        x.numkeys ++;
       
    }
    
    /**
     * key, pos will be added to Btree T. 
     * assumes pos is a valid file position, and all keys are strings.
     * @param T
     * @param key
     * @param pos 
     */
    public void Insert(BTree T, String key, Long pos){
        BNode root = T.theRoot;
        if(root.numkeys == MAXKEYS-1){
            BNode s = new BNode();
            T.theRoot = s;
            s.isLeaf = false;
            s.numkeys = 0;
            s.children[0] = root;
            SplitChild(s, 0);
            InsertNonFull(s,key,pos);
        } else {
            InsertNonFull(root, key,pos);
        } 
    }
    private void InsertNonFull(BNode x, String key, Long pos){
        int i = x.numkeys-1;
        if(x.isLeaf){
            while (i >= 0 && key.compareTo(x.keys[i]) < 0) {                
                x.keys[i+1] = x.keys[i];
                x.values[i+1] = x.values[i];
                i--;    
            }
            x.keys[i+1] = key;
            x.values[i+1] = pos;
            x.numkeys++;
        }
        else{
            //System.out.println("Loader.BTree.InsertNonFull() x is not a leaf");
            //System.out.println("initial i="+i+"");
            while (i >= 0 && key.compareTo(x.keys[i]) < 0) {           
                i--;
            }
            i++;
            if(x.children[i] == null){
                x.children[i] = new BNode();
                x.children[i].isLeaf = true;
            }
            if(x.children[i].numkeys == MAXKEYS-1){
                //System.out.println("Loader.BTree.InsertNonFull()");
                //System.out.println("numKey_i = "+);
                SplitChild(x, i);
                if(key.compareTo(x.keys[i]) > 0)
                    i++;
            }
            InsertNonFull(x.children[i], key, pos);
        }          
    }
}
