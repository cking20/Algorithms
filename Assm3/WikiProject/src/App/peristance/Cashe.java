/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.peristance;

/**
 * Implements a Hashmap to save data in memory, persists Pages to disk, and acts
 *      as intermediary between application and downloading
 * 
 */
public class Cashe {
    private int size = 32;
    private int rows = 16;
    private static long timeRange = 2628000000l;//one month
    App.Page[][] thePageCashe;
    App.peristance.ShortestPathGraph [][] thePathCashe;
    
    Persist saveManager;

    public Cashe() {
        thePageCashe  = new App.Page[size][rows];
        thePathCashe = new App.peristance.ShortestPathGraph[size][rows];
        saveManager = new Persist();
    }
    
    /**
     * 
     * @param title
     * @return null on the data has not been saved properly which implies a bad link(mainly used for graph)
     */
    public App.Page getPage(String title){
        App.Page p = SearchInMemPage(title);
        if(p == null){
            if(saveManager.hasPage(title)){
                p = saveManager.LoadPageData(title);
                //check if the page should be updated
                if(p.TimeDownloaded < System.currentTimeMillis() - timeRange){
                    //delete file
                    System.out.println("App.peristance.Cashe.getPage()Deleting File");
                    saveManager.RemoveData(title);
                    //set p to null so it will be redownloaded;
                    p = null;
                }
            }
            if(p == null){
                p = App.DataCollection.downloadPage(title);
                if(p != null && p.links != null){
                    if(!saveManager.SavePageData(p))
                        return null;
                    this.Add(p);
                }
            } else{
                this.Add(p);
            }   
        }
        return p;
    }
    
    /**
     * May return null, need to check and recalculate if needed
     * @param title
     * @return 
     */
    public App.peristance.ShortestPathGraph getSavedPath(String title){
        if(title.compareTo("")== 0)
            title = "Stack OverFlow";
          App.peristance.ShortestPathGraph p = SearchInMemPath(title);
          if(p == null){
            if(saveManager.hasPath(title)){
                p = saveManager.LoadPathData(title);
                //check if the page should be updated
                if(p.timeCalced < System.currentTimeMillis() - timeRange || p.dist.length != Persist.GetFileNames().size()){
                    //delete file
                    System.out.println("App.peristance.Cashe.getPage()Deleting File");
                    saveManager.RemoveData(title);
                    //set p to null so it will be redownloaded;
                    p = null;
                }
            }
        }
        return p;
    
    }
    
    /**
     * Saves the path to disk
     * @param path
     * @return 
     */
    public boolean savePath(App.peristance.ShortestPathGraph path){
        return saveManager.SavePathData(path);  
    }
    
    /**
     * Saves the page to disk
     * @param page
     * @return 
     */
    public boolean savePage(App.Page page){
        return saveManager.SavePageData(page);  
    }
    
    /**
     * ASSUMES THE PAGE HAS BEEN DOWNLOADED. WILL NOT DOWNLOAD A NEW PAGE
     * Loads a page, checking memory first, then disk
     * @param title requires the .ser ending
     * @return 
     */
    public App.Page getDownloadedPage(String title){
        App.Page p = SearchInMemPage(title);
        if(p == null){
            if(saveManager.hasPage(title)){
                p = saveManager.LoadPageData(title);
            }
            //System.out.println(title+" Not downloaded");
        }
        return p;
    }
    
    /**
     * Searches the cashe for the page with title url
     * @param url
     * @return 
     */
    private App.Page SearchInMemPage(String url){
        int hash = hash(url);
        for(int i = 0; i < rows; i++){
            if(thePageCashe[hash][i] != null){
                if(thePageCashe[hash][i].title.compareTo(url) == 0)
                    return thePageCashe[hash][i];
            }
        }
        return null; 
    }
    
    /**
     * Searches the cashe for the path with root url
     * @param url
     * @return 
     */
    private App.peristance.ShortestPathGraph SearchInMemPath(String url){
        int hash = hash(url);
        for(int i = 0; i < rows; i++){
            if(thePathCashe[hash][i] != null){
                if(thePathCashe[hash][i].title.compareTo(url) == 0)
                    return thePathCashe[hash][i];
            }
        }
        return null; 
    }
    
    /**
     * Checks attempts to add the data to the map, if its full, it evicts the LRU
     * @param hm 
     */
    private void Add(App.Page hm){
        if(hm == null)
            return;
        int hash = hash(hm.title);
        int i;
        for(i = 0; i < rows; i++){
            if(thePageCashe[hash][i] == null){
                thePageCashe[hash][i] = hm;
            }
        }
        if(i == rows){//row full, evict LRU
            int row = Evict(hash);
            thePageCashe[hash][row] = hm;
        }
    }
    
    /**
     * Makes room in a given column
     * @param column
     * @return the row of the freed space
     */
    public int Evict(int column){
        int row = FindLRU(column);
        thePageCashe[column][row] = null;
        return row;
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
            if(thePageCashe[column][i] == null){
                return i;
            }
            if(thePageCashe[column][i].TimeDownloaded < oldestDate){
                oldestDate = thePageCashe[column][i].TimeDownloaded;
                oldestIndex = i;
            }
        }
        return oldestIndex;
    }
    
    /**
     * Hashes the key to an array index
     * @param key
     * @return 
     */
    private int hash(String key){
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
