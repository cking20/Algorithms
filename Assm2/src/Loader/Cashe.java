package Loader;

import assignment1.HashMap;

/**
 * Keeps track of datasets in file locations
 * Evicts old datasets after one week
 * All saving and loading of data sets goes through this, checking to see
 *  if the url is stored here before downloading again.
 */
public class Cashe {
    private static long timeRange = 2628000000l;//one month
    private CasheMap casheMap;
    private Persistance persist;
    
    HashMap[] hashMaps;
        
    public Cashe() {
        //I know this is bad, but an early design descision forced me to strongly
        //  link Cashe and Persistance instead of making them one
        casheMap = new CasheMap();
        persist = new Persistance();    
    }
    
    /**
     * Save the persistent file
     */    
    public void Save(){
        persist.SaveTree();
    }
    /**
     * Defragment the persistent file
     */
    public void Defragment(){
        persist.Defragment();
    }
    /**
     * Stores the hashmap into memory and saves it to a file
     * @param newHash 
     */
    public void Add(HashMap newHash){
        casheMap.Add(newHash);
        persist.SaveStateData(newHash);         
    }
    /**
     * Will load an item from memory first if it has it, if not, from file  
     * @param url
     * @return 
     */
    public HashMap LoadItem(String url){
        HashMap hm;
        hm = casheMap.Search(url);
        if(hm == null){//not in memory
            hm = persist.LoadStateData(url);
            hm = CheckIfReplaceData(hm);
            casheMap.Add(hm);//load it to memory
        }
        return hm;
    }
    
    /**
     * use to see if an item is saved
     * @param url
     * @return 
     */
    public boolean HasURL(String url){
        HashMap hm = casheMap.Search(url);
        if(hm == null)//not saved in memory
            return persist.haveSavedData(url);
        else //is saved in memory
            return true;
    }
    
    
    /**
     * Checks to see if the data is stale or not
     * @param hm
     * @return 
     */
    public HashMap CheckIfReplaceData(HashMap hm){
        if(System.currentTimeMillis() - timeRange > hm.getTimeDownloaded()){
            System.out.println("OLD DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            hm = ReplaceData(hm.getURL(), hm.StateID, hm.Retrieve().getDatatype());
        }
        return hm;  
    }
    
    /**
     * Downloads and Saves the new data to the file and updates the Btree value
     * @param url
     * @param locaitonID
     * @param dataType
     * @return 
     */
    private HashMap ReplaceData(String url, int locaitonID, String dataType){
        HashMap hm = new HashMap();
        assignment1.Assignment1.gather(hm, url, locaitonID, dataType); 
        persist.theTree.Replace(persist.theTree.theRoot, url, persist.SaveStateDataDefrag(hm));
        return hm;
    }
    
}
