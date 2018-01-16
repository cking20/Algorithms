package assignment1;

/**
 *
 * @author chrisrk192
 */
public class Value {
    private int key,value, hash,locationVal;
    private String date, datatype, station, attributes,uid;
    private String keyString;
    
    public Value(){
        value = 0;
        key = 0;
        hash = 0;
        date = "";
        datatype = "";
        station = "";
        attributes = "";
        uid = "";
        keyString = "";
        locationVal = 0;
    }
    
    /**
     * Returns a deep copy of this object.
     * 
     * @return deep copy of this object
    */
    public Value Clone() {
        Value clonedItem = new Value();
        clonedItem.setValue(value);
        clonedItem.setKey(key);
        clonedItem.setDate(date);
        clonedItem.setDatatype(datatype);
        clonedItem.setStation(station);
        clonedItem.setAttributes(attributes);
        clonedItem.setUid(uid);
        clonedItem.setKeyString(keyString);
               
        return clonedItem;
    }
    
    /**
     * Compares an object's key with this object's key
     * 
     * @param e an element to make the comparison to.
     * @return true on the same key, false on different
    */
    public boolean equals(Element e) {
        if (e.getData().getKey() == key) {
            return true;
        }
        return false;
    }
    
    /**
     * Sets the Key based on the Station ID and the DataType
     * Preconditions: Station ID and DataType must be set
     */
    public void calcKey(){
        int stationInt,dataTypeInt;
    
        key =  calcIntVal(station) + calcIntVal(datatype);
        setKeyString(station + datatype );
    }
    
    /**
     * @param keyString the keyString to set
     */
    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }
    
    private int calcIntVal(String string){
        int val = 0;
        for(int i = 0; i < string.length(); i ++){
            val = val << Character.SIZE;
            val += (int)string.charAt(i);
        }
        return val;
    }
    /**
     * @return the key
    */           
    public int getKey() {
        return key;
    }
    
    /**
     * @param l location value
    */ 
    public void setLocationVal(int l){
        this.locationVal = l;
    }
    
    /**
     * @return the location value
    */ 
    public int getLocationVal(){
        return locationVal;
    }
    
    /**
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }
    
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    
    /**
     * @return the datatype
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * @param datatype the datatype to set
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    /**
     * @return the station
     */
    public String getStation() {
        return station;
    }

    /**
     * @param station the station to set
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * @return the attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the hash
     */
    public int getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return the keyString
     */
    public String getKeyString() {
        return keyString;
    }

    

    
}

