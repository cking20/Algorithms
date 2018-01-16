package Loader;

import assignment1.HashMap;
import assignment1.Value;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * All File IO is handled here. The cashe make requests through this class.
 * 
 */
public class Persistance {
    private final int URL_CHAR_NUM = 150;
    private final int DATE_CHAR_NUM = 10;
    private final int STTN_CHAR_NUM = 20;
    private final int ATTR_CHAR_NUM = 10;
    private final int TYPE_CHAR_NUM = 10;
    
    private int numStates = 56;
    private String BTreeFileName = "BTree.ser";
    public BTree theTree;
    private String RAFileName = "RandomAccessFile.dat";
    private FileChannel RAFchan;
    private int metaSize;
    private int readingSize;
    public Persistance(){
        LoadTree();
        metaSize = 
            Integer.BYTES +                 //lenght of record/number of values 
            Integer.BYTES +                 //StateData ID 
            Long.BYTES +                    //timeDownloaded 
            URL_CHAR_NUM +                  //URL
            DATE_CHAR_NUM +                 //dataDateFrom  
            DATE_CHAR_NUM ;                 //dataDateTo 
        readingSize = 
            Integer.BYTES +//value
            Integer.BYTES +//key
            DATE_CHAR_NUM +//dataDate
            TYPE_CHAR_NUM +//type
            STTN_CHAR_NUM + //station
            ATTR_CHAR_NUM;//attributes
    }
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
            FileInputStream inFile = new FileInputStream(BTreeFileName);
            ObjectInputStream in = new ObjectInputStream(inFile);
            theTree = (BTree) in.readObject();
            in.close();
            inFile.close();
      }catch(IOException | ClassNotFoundException e) { 
          theTree = new BTree();
      }
    }
    
    /**
     * Determines if the url is saved on disk or not
     * @param url
     * @return true on url saves, false otherwise
     */
    public boolean haveSavedData(String url){
        if(theTree.Search(theTree.theRoot, url) > -1)
            return true;
        return false;
    }
    
    /**
     * Saves a dataset to disk and saves the file location reference to the Btree
     * @param hM 
     */
    public void SaveStateData(HashMap hM){  
        String key = hM.getURL();
        //see if already saved
        Long pos = theTree.Search(theTree.theRoot, key);
        if(pos == -1){//not yet added
            try {
                OpenRAFWrite();
                long endOF = RAFchan.size();//in bytes
                int hashNum = hM.getCount();
                System.out.println("Hash count: " + hashNum);
                int totalSizeReq = metaSize + readingSize * hashNum;
                ByteBuffer bb = ByteBuffer.allocate(totalSizeReq);
                //META
                bb.putInt(hM.getCount());
                bb.putInt(hM.StateID);
                bb.putLong(hM.getTimeDownloaded());
                bb.put(getByteArrayFromString(hM.getURL(), URL_CHAR_NUM));
                bb.put(getByteArrayFromString(hM.FromDate, DATE_CHAR_NUM));
                bb.put(getByteArrayFromString(hM.ToDate, DATE_CHAR_NUM));
                //END OF META
                bb.flip();
                bb.rewind();
                RAFchan.write(bb);
                bb.clear();
               
                hM.Reset();
                Value v;
                int i=0;
                ByteBuffer rb = ByteBuffer.allocate(readingSize);
                while(hM.traverse()){
                    //System.out.println(i++ +"");
                    v = hM.Retrieve();
                    hM.map[hM.getCurrent()].GetNext();
                    
                    rb.putInt(v.getValue());
                    rb.putInt(v.getKey());
                    //needed to ensure the correct size of the strings
                    rb.put(getByteArrayFromString(v.getDate(),DATE_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getDatatype(),TYPE_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getStation(),STTN_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getAttributes(),ATTR_CHAR_NUM));
                    rb.flip();
                    rb.rewind();
                    RAFchan.write(rb);
                    rb.clear();
                }
                //update tree
                theTree.Insert(theTree, key, endOF);
                CloaseRAF();
            } catch (IOException e) {
                System.err.println("DATA NOT SAVED");
            }    
        } 
    }
    
    /**
     * Helper function to ensure data is saved properly
     * Accepts sizes of 10,20, and 150
     * @param s 
     * @param size
     * @return a byte array of proper size
     */
    private byte [] getByteArrayFromString(String s, int size){
        String tenTemp = "          ";//10
        String twentyTemp = "                    ";//20
        String hundTemp = "                                                "
                + "                                                    "
                + "                                                  ";//150
        String template;
        switch(size){
            case DATE_CHAR_NUM:
                template = tenTemp;
                break;
            case STTN_CHAR_NUM:
                template = twentyTemp;
                break;
            case URL_CHAR_NUM:
                template = hundTemp;
                break;
            default:
                template = tenTemp;    
        }
        byte[] ba = template.getBytes();
        byte[] sba = s.getBytes();
        for(int i = 0; i < Character.BYTES * size && i < sba.length && i < ba.length;i++){
            ba[i] = sba[i];
        }
        return ba;
    }
    
    /**
     * the ByteBuffer is incremented on use
     * @param bb
     * @param stringSize
     * @return String value of the bytebuffer at current position with length stringSize
     */
    private String getStringFromByteBuffer(ByteBuffer populated, int stringSize){
        int offset = populated.position();
        byte[] wholeArray = populated.array();
        byte[] portion = new byte[stringSize];
        int i;
        for(i = 0;i < portion.length;i++){
            portion[i] = wholeArray[i + offset];
        }
        populated.position(i + offset);
        String ret = new String(portion, Charset.forName("UTF-8"));
        
        //System.out.println(ret + "end buff print\n");
        return ret;
        
    }
    
    public void RemoveStateData(String url){
        if(haveSavedData(url)){
            //find where it is
            long readPos = theTree.Search(theTree.theRoot, url);
            //remove it from the file
            theTree.Remove(theTree.theRoot, url);
            
        }
        
    }
    
    public HashMap LoadStateData(String url){
        //search the Btree
        //System.out.println("Loader.Persistance.LoadStateData()");
        //System.out.println("url searching: " + url);
        //System.out.println("does Btree contian url? : " + haveSavedData(url));
        long readPos = theTree.Search(theTree.theRoot, url);
        //System.out.println("Found poision: " + readPos);
        if(readPos > -1){
            try{
                //OpenRAFRead();
                if(!OpenRAFRead())
                    return null;
                HashMap theMap = new HashMap();
                ByteBuffer meta = ByteBuffer.allocate(metaSize);
                //load meta data
                
                RAFchan.read(meta, readPos);
                meta.flip();
                int numReadings = meta.getInt();
                int stateID = meta.getInt();
                long timeDLed = meta.getLong();
                String readUrl = getStringFromByteBuffer(meta, URL_CHAR_NUM);  
                String fromDate = getStringFromByteBuffer(meta, DATE_CHAR_NUM);
                String toDate = getStringFromByteBuffer(meta, DATE_CHAR_NUM);
                
                theMap.setURL(readUrl);
                theMap.setTimeDownloaded(timeDLed);
                theMap.FromDate = fromDate;
                theMap.ToDate = toDate;
                theMap.StateID = stateID;
                System.out.println(
                        "Loading meta with vals:" + numReadings 
                       + "\n items," + "stateID " + stateID + "\nfrom " +fromDate 
                       + ", to " + toDate + "\ndownloaded at " + timeDLed);
                if(meta.hasRemaining()){
                    System.err.println("NOT ALL THE META BUFFER WAS USED ON LOAD");
                    getStringFromByteBuffer(meta, meta.capacity() - meta.position());
                }
                if(stateID > 60)//safegaurd against bad data
                    return null;
                //load one reading at a time
                readPos += metaSize;
                Value v;
                ByteBuffer bb = ByteBuffer.allocate(readingSize);
                for(int i = 0; i < numReadings; i++){
                    v = new Value();
                    bb.clear();
                    RAFchan.read(bb, readPos);
                    bb.rewind();
                    v.setLocationVal(theMap.StateID);
                    v.setValue(bb.getInt());//value
                    v.setKey(bb.getInt());//key
                    v.setDate(getStringFromByteBuffer(bb, DATE_CHAR_NUM));//date
                    v.setDatatype(getStringFromByteBuffer(bb, TYPE_CHAR_NUM));//datatype
                    v.setStation(getStringFromByteBuffer(bb, STTN_CHAR_NUM));//station
                    v.setAttributes(getStringFromByteBuffer(bb, ATTR_CHAR_NUM));//attr
                    v.calcKey();
                    readPos += readingSize;
                    //System.out.println("Adding Item: val ="+ v.getValue()
                    //        + " key = " + v.getKey()+" Date: "+v.getDate()+" type = "
                    //        +v.getDatatype() + " arrt = "+ v.getAttributes()+"Station = "
                    //        +v.getStation());  
                    theMap.add(v);
                }
                CloaseRAF();
                return theMap;
            } catch(IOException e){
                System.err.println("IO EXCEPTION");
                return null;
            } catch(BufferUnderflowException uf){
                System.err.println("BUFFER UNDEFLOW EXP");
                uf.printStackTrace();
                return null;
            }
            
        } 
        System.err.println("attempting load on non existing url");
        return null;
    }
    
    /**
     * Creates a new file and copies all relivant segments from the old file
     * using the bTree references, then deletes the old file and sets the name
     * of the new file to the the old file name
     */
    public void Defragment(){
        BTree newTree = new BTree();
        Defragment(theTree.theRoot, RAFileName, "temp.dat");
        Path path = Paths.get(RAFileName);
        //theTree = newTree;
        File file = new File(RAFileName);
        file.delete();
        File file2 = new File("temp.dat");
        file2.renameTo(file);
    }
    private void Defragment(BNode aRoot, String oldFileName, String tempFileName){
        HashMap hm;
        int currentKeyIndex = 0;
        int currentChildIndex = 0;
        for(int i = 0; i < aRoot.numkeys; i++){
            RAFileName = oldFileName;
            System.out.println(aRoot.keys[i]);
            System.out.println(aRoot.values[i]);
            hm = LoadStateDataDefrag(aRoot.values[i]);//load from old
            RAFileName = tempFileName;
            if(hm != null)//make sure the data is good
                aRoot.values[i] = SaveStateDataDefrag(hm); //save in RAF update tree val
            else{
                System.err.println("REMOVING BAD DATA");
                RemoveStateData(aRoot.keys[i]);
            }
            RAFileName = oldFileName;
        }
            
        //go thorugh every key in this node and save it
        
        while ((currentChildIndex < 32) && aRoot.children[currentChildIndex] != null) {
            //System.out.println("Loader.Persistance.Defragment()calling DEFRAG");
            Defragment(aRoot.children[currentChildIndex], oldFileName, tempFileName);
            currentChildIndex++;
        }
        //delete the original, change the file of 2 to orig.
        
    }
    
    /**
     * PRECONDITION: CHANGE RAFILENAME to the new file name
     * The Btree is not updated, must be done manualy
     * Best used for defragmenting, or replacing
     * @param hM
     * @param tree 
     */
    public long SaveStateDataDefrag(HashMap hM){  
        String key = hM.getURL();
        //see if already saved
        //Long pos = tree.Search(tree.theRoot, key);
        //if(pos == -1){//not yet added
            try {
                OpenRAFWrite();
                long endOF = RAFchan.size();//in bytes
                int hashNum = hM.getCount();
                System.out.println("Hash count: " + hashNum);
                int totalSizeReq = metaSize + readingSize * hashNum;
                ByteBuffer bb = ByteBuffer.allocate(totalSizeReq);
                //META
                bb.putInt(hM.getCount());
                bb.putInt(hM.StateID);
                bb.putLong(hM.getTimeDownloaded());
                bb.put(getByteArrayFromString(hM.getURL(), URL_CHAR_NUM));
                bb.put(getByteArrayFromString(hM.FromDate, DATE_CHAR_NUM));
                bb.put(getByteArrayFromString(hM.ToDate, DATE_CHAR_NUM));
                //END OF META
                bb.flip();
                bb.rewind();
                RAFchan.write(bb);
                bb.clear();
               
                hM.Reset();
                Value v;
                int i=0;
                ByteBuffer rb = ByteBuffer.allocate(readingSize);
                while(hM.traverse()){
                    //System.out.println(i++ +"");
                    v = hM.Retrieve();
                    hM.map[hM.getCurrent()].GetNext();
                    
                    rb.putInt(v.getValue());
                    rb.putInt(v.getKey());
                    //needed to ensure the correct size of the strings
                    rb.put(getByteArrayFromString(v.getDate(),DATE_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getDatatype(),TYPE_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getStation(),STTN_CHAR_NUM));
                    rb.put(getByteArrayFromString(v.getAttributes(),ATTR_CHAR_NUM));
                    rb.flip();
                    rb.rewind();
                    RAFchan.write(rb);
                    rb.clear();
                }
                //update tree
                //tree.Insert(tree, key, endOF);
                CloaseRAF();
                return endOF;
            } catch (IOException e) {
                System.err.println("DATA NOT SAVED");
            }    
        //} 
        return -1;
    }
    
    /**
     * Loads directly using a file position instead of a url
     * @param readPos
     * @return 
     */
    public HashMap LoadStateDataDefrag(long readPos){
        //search the Btree
        //System.out.println("Loader.Persistance.LoadStateData()");
        //System.out.println("url searching: " + url);
        //System.out.println("does Btree contian url? : " + haveSavedData(url));
        //long readPos = theTree.Search(theTree.theRoot, url);
        //System.out.println("Found poision: " + readPos);
        if(readPos > -1){
            try{
                //OpenRAFRead();
                if(!OpenRAFRead())
                    return null;
                HashMap theMap = new HashMap();
                ByteBuffer meta = ByteBuffer.allocate(metaSize);
                //load meta data
                if(RAFchan.size() - readPos < metaSize){
                    System.err.println("Would be buffer underflow");
                    CloaseRAF();
                    return null;//protect afainst buffer underflow
                }
                RAFchan.read(meta, readPos);
                meta.flip();
                int numReadings = meta.getInt();
                int stateID = meta.getInt();
                long timeDLed = meta.getLong();
                String readUrl = getStringFromByteBuffer(meta, URL_CHAR_NUM);  
                String fromDate = getStringFromByteBuffer(meta, DATE_CHAR_NUM);
                String toDate = getStringFromByteBuffer(meta, DATE_CHAR_NUM);
                
                theMap.setURL(readUrl);
                theMap.setTimeDownloaded(timeDLed);
                theMap.FromDate = fromDate;
                theMap.ToDate = toDate;
                theMap.StateID = stateID;
                //System.out.println(
                //        "Loading meta with vals:" + numReadings 
                //       + "\n items," + "stateID " + stateID + "\nfrom " +fromDate 
                //       + ", to " + toDate + "\ndownloaded at " + timeDLed);
                if(meta.hasRemaining()){
                    System.err.println("NOT ALL THE META BUFFER WAS USED ON LOAD");
                    getStringFromByteBuffer(meta, meta.capacity() - meta.position());
                }
                if(numReadings > 1000){//safegaurd against bad data
                    System.err.println("Bad Data");
                    CloaseRAF();
                    return null;
                }
                //load one reading at a time
                readPos += metaSize;
                Value v;
                ByteBuffer bb = ByteBuffer.allocate(readingSize);
                for(int i = 0; i < numReadings; i++){
                    v = new Value();
                    bb.clear();
                    RAFchan.read(bb, readPos);
                    bb.rewind();
                    v.setLocationVal(theMap.StateID);
                    v.setValue(bb.getInt());//value
                    v.setKey(bb.getInt());//key
                    v.setDate(getStringFromByteBuffer(bb, DATE_CHAR_NUM));//date
                    v.setDatatype(getStringFromByteBuffer(bb, TYPE_CHAR_NUM));//datatype
                    v.setStation(getStringFromByteBuffer(bb, STTN_CHAR_NUM));//station
                    v.setAttributes(getStringFromByteBuffer(bb, ATTR_CHAR_NUM));//attr
                    v.calcKey();
                    readPos += readingSize;
                    //System.out.println("Adding Item: val ="+ v.getValue()
                    //        + " key = " + v.getKey()+" Date: "+v.getDate()+" type = "
                    //        +v.getDatatype() + " arrt = "+ v.getAttributes()+"Station = "
                    //        +v.getStation());  
                    theMap.add(v);
                }
                CloaseRAF();
                return theMap;
            } catch(IOException e){
                System.err.println("IO EXCEPTION");
                CloaseRAF();
                return null;
            } catch(BufferUnderflowException uf){
                System.err.println("BUFFER UNDEFLOW EXP");
                CloaseRAF();
                //uf.printStackTrace();
                return null;
            }
            
        } 
        System.err.println("attempting load on non existing url");
        CloaseRAF();
        return null;
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

