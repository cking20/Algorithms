/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.peristance;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * All File IO is handled here. The cashe make requests through this class.
 * 
 */
public class Persist {
    private String BTreeFileName = "./persist/BTree.ser";
    public BTree theTree;
    private String RAFileName = "./persist/RandomAccessFile.ser";
    private FileChannel RAFchan;
    private static String prefix = "./pages/";
    private String sufix = ".ser";
    private static String dir = "pages";
    private static String pathDir = "paths";
    private static String pathPrefix = "./paths/";
    
    /**
     * Creates the directories if they do not already exist
     */
    public Persist(){
        File f = new File(dir);
        File g = new File(pathDir);
        if (!f.exists()) {
            try{
                f.mkdir();    
            } 
            catch(SecurityException se){
                se.printStackTrace();
            }          
        }
        if (!g.exists()) {
            try{
                g.mkdir();    
            } 
            catch(SecurityException se){
                se.printStackTrace();
            }          
        }
        LoadTree(); 
    }
    
    /**
     * @return the directory that stores the pages
     */
    public static String getDir(){
        return dir;
    }
    
    /**
     * Determines if the url is saved on disk or not
     * @param url
     * @return true on url saves, false otherwise
     */
    public boolean haveSavedData(String url){
        //if(theTree.Search(theTree.theRoot, url) > -1)
        File f = new File(prefix+url+sufix);
        if(f.exists())
            return true;
        return false;
    }
    
    /**
     * Saves a dataset to disk and saves the file location reference to the Btree
     * @param page 
     * @return  
     */
    public boolean SavePageData(App.Page page){ 
        if(page == null)
            return false;
        String key = page.title;
            try {
                
                File f = new File(prefix+page.title+sufix);
                if(f.exists())
                    f.delete();
                f.createNewFile();
                
                FileOutputStream fos = new FileOutputStream(prefix+page.title+sufix);
                long endOF = fos.getChannel().size();
                //System.out.println("Saving "+page.title+" EOF as" + endOF);
                
                fos.getChannel().position(endOF);
               
                ObjectOutputStream oOs = new ObjectOutputStream(fos);
                oOs.writeObject(page);
                oOs.close();
                fos.close();
                //update tree
                theTree.Insert(theTree, key, endOF);
                SaveTree();
                //CloaseRAF();
                return true;
            } catch (IOException e) {
                System.err.println("DATA NOT SAVED");
                return false;
            }    
        //} 
    }
    
    /**
     * Loads the page with title url from disk
     * @param url
     * @return 
     */
    public App.Page LoadPageData(String url){
        
        //search the Btree
        App.Page p;
            try{
                
                try {
                    File f = new File(prefix+url+sufix);
                    if(f.exists()){
                        FileInputStream fis = new FileInputStream(prefix+url+sufix);
                        ObjectInputStream oIs = new ObjectInputStream(fis);

                        p = (App.Page)oIs.readObject();
                        oIs.close();
                    } else
                        p = null;
                } catch(StreamCorruptedException e){
                    e.printStackTrace();
                    return null;
                }
                
                try{
                    //System.out.println("---"+p.title);
                    //int i = p.ID;
                } catch(NullPointerException e){
                    //e.printStackTrace();
                    return null;
                }
                return p;
            }catch(IOException e) { 
                System.err.println("IO EXCEPTION");
                e.printStackTrace();
                return null;
            }catch(ClassNotFoundException e) { 
                System.err.println("ClassNotFoundException");
                e.printStackTrace();
                return null;
            } catch(BufferUnderflowException uf){
                System.err.println("BUFFER UNDEFLOW EXP");
                uf.printStackTrace();
                return null;
            } 
            
        //} 
        //System.out.println("attempting load on non existing url");
        //return null;
    }
    
    /**
     * Saves a dataset to disk and saves the file location reference to the Btree
     * @param path 
     * @return  
     */
    public boolean SavePathData(App.peristance.ShortestPathGraph path){ 
        if(path == null)
            return false;
        String key = path.title;
            try {
                
                File f = new File(pathPrefix+path.title+sufix);
                f.createNewFile();
                
                FileOutputStream fos = new FileOutputStream(pathPrefix+path.title+sufix);
                long endOF = fos.getChannel().size();
                //System.out.println("Saving "+path.title+" EOF as" + endOF);
                
                fos.getChannel().position(endOF);
               
                ObjectOutputStream oOs = new ObjectOutputStream(fos);
                oOs.writeObject(path);
                oOs.close();
                fos.close();
                //update tree
                //theTree.Insert(theTree, key, endOF);
                //SaveTree();
                //CloaseRAF();
                return true;
            } catch (IOException e) {
                System.err.println("DATA NOT SAVED");
                return false;
            }    
        //} 
    }
    
    /**
     * Loads a shortest path with root at page url
     * @param url
     * @return 
     */
    public App.peristance.ShortestPathGraph LoadPathData(String url){
        App.peristance.ShortestPathGraph p;
            try{
                
                try {
                    File f = new File(pathPrefix+url+sufix);
                    if(f.exists()){
                        FileInputStream fis = new FileInputStream(pathPrefix+url+sufix);
                        ObjectInputStream oIs = new ObjectInputStream(fis);

                        p = (App.peristance.ShortestPathGraph)oIs.readObject();
                        oIs.close();
                    } else
                        p = null;
                } catch(StreamCorruptedException e){
                    e.printStackTrace();
                    return null;
                }
                
                try{
                    //System.out.println("---"+p.title);
                    //int i = p.ID;
                } catch(NullPointerException e){
                    //e.printStackTrace();
                    return null;
                }
                return p;
            }catch(IOException e) { 
                System.err.println("IO EXCEPTION");
                e.printStackTrace();
                return null;
            }catch(ClassNotFoundException e) { 
                System.err.println("ClassNotFoundException");
                e.printStackTrace();
                return null;
            } catch(BufferUnderflowException uf){
                System.err.println("BUFFER UNDEFLOW EXP");
                uf.printStackTrace();
                return null;
            } 
            
        //} 
        //System.out.println("attempting load on non existing url");
        //return null;
    }
    
    /**
     * Loads the name of all pages saved to disk
     * @return 
     */
    public static ArrayList<String> GetFileNames(){
        File dirc = new File(dir);
        File [] f = dirc.listFiles();
        ArrayList<String> fNames = new ArrayList();
        for(int i = 0; i < f.length;i++){
            fNames.add(f[i].getName());//sets the names to a parallel array for easy name loop up
            //System.out.println("Adding "+f[i].getName());
        }
        return fNames;
    }
    
    /**
     * Tells if the page hase been saved to disk
     * @param url
     * @return 
     */
    public boolean hasPage(String url){
        File f = new File(prefix+url+sufix);
        if(f.exists())
            return true;
        return false;
    }
    
    /**
     * Tells if the shortest path has been saved to disk
     * @param url
     * @return 
     */
    public boolean hasPath(String url){
        File f = new File(pathPrefix+url+sufix);
        if(f.exists())
            return true;
        return false;
    }
    
    
    
    
    
    //OLD, UNUSED, POSSIBLY USEFUL FUNCTIONS FROM ASSIGNMENT 2
    
     /**
     * Saves a the Btree
     */
    public void SaveTree(){
        try{
            FileOutputStream outFile =
            new FileOutputStream(BTreeFileName);
            ObjectOutputStream oOs = new ObjectOutputStream(outFile);
            oOs.writeObject(theTree);
            oOs.close();
            outFile.close();
                    
        } catch(IOException e){
        }           
    }
    
    /**
     * Loads up the BTreee
     */
    private void LoadTree(){
        try {
            File f = new File(RAFileName);
            f.createNewFile();
            FileInputStream inFile = new FileInputStream(BTreeFileName);
            ObjectInputStream in = new ObjectInputStream(inFile);
            theTree = (BTree) in.readObject();
            in.close();
            inFile.close();
      }catch(IOException | ClassNotFoundException e) { 
          theTree = new BTree();
      }
    }
    
    public static App.Page LoadFile(File f){
        App.Page p;
            try{
                try {
                    if(f.exists()){
                        FileInputStream fis = new FileInputStream(prefix+f.getName());
                        ObjectInputStream oIs = new ObjectInputStream(fis);

                        p = (App.Page)oIs.readObject();
                        oIs.close();
                    } else
                        p = null;
                } catch(StreamCorruptedException e){
                    e.printStackTrace();
                    return null;
                }
                
                try{
                    //System.out.println("---"+p.title);
                    //int i = p.ID;
                } catch(NullPointerException e){
                    //e.printStackTrace();
                    return null;
                }
                return p;
            }catch(IOException e) { 
                System.err.println("IO EXCEPTION");
                e.printStackTrace();
                return null;
            }catch(ClassNotFoundException e) { 
                System.err.println("ClassNotFoundException");
                e.printStackTrace();
                return null;
            } catch(BufferUnderflowException uf){
                System.err.println("BUFFER UNDEFLOW EXP");
                uf.printStackTrace();
                return null;
            } 
            
        //} 
        //System.out.println("attempting load on non existing url");
        //return null;
    }
    
    /**
     * Deletes the page from persistent storage.
     * @param url title of page 
     */
    public void RemoveData(String url){
        if(haveSavedData(url)){
            //find where it is
            long readPos = theTree.Search(theTree.theRoot, url);
            //remove it from the file
            theTree.Remove(theTree.theRoot, url);
            
            File f = new File(url);
            if(f.exists())
                f.delete();
            
        }
        
    }
    
    /**
     * Creates a new file and copies all relivant segments from the old file
     * using the bTree references, then deletes the old file and sets the name
     * of the new file to the the old file name
     */
    public void Defragment(){
        BTree newTree = new BTree();
        //Defragment(theTree.theRoot, RAFileName, "temp.dat");
        Path path = Paths.get(RAFileName);
        //theTree = newTree;
        File file = new File(RAFileName);
        file.delete();
        File file2 = new File("temp.dat");
        file2.renameTo(file);
    }
    
    /**
     * Open the RAF in write/append mode
     * @return file position of the thing to allocated;
     */
    private void OpenRAFWrite(){
        try {
            Set options = new HashSet();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);
                options.add(StandardOpenOption.WRITE);
            Path path = Paths.get(RAFileName);
            RAFchan = FileChannel.open(path, options);
            
        } catch (Exception e) {
            System.err.println("Error in RAF file opening");
            e.printStackTrace();
        }
    }
    
    /**
     * Open the RAF file in read mode
     */
    private boolean OpenRAFRead(){
        try {
            Set options = new HashSet();
		options.add(StandardOpenOption.CREATE);
                options.add(StandardOpenOption.READ);
            Path path = Paths.get(RAFileName);
            RAFchan = FileChannel.open(path, options);
            return true;
        } catch (Exception e) {
            System.err.println("Error opening file");
            //CloaseRAF();
            return false;
        }
    }
    
    /**
     * Close the RAF file
     */
    private void CloaseRAF(){
        try{
            RAFchan.close();
        }catch(IOException e){
            System.err.println("Error closing file");
        }
    }
}
