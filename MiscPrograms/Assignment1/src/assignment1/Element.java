/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

/**
 *
 * @author chrisrk192
 */
public class Element {
    //private  ;
    private int key,value, hash;
    private String date, datatype, station, attributes;
    
    
    
    public Element(){
        key = 0;
        hash = 0;
        date = "";
        datatype = "";
        station = "";
        attributes = "";
    }
    /**
     * @return the key
     */
    
    //@Override
    public boolean equals(Element e) {
        if (e.getKey() == key) {
            return true;
        }
        return false;
    }
    
    
     
           
    public int getKey() {
        return key;
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

    public void calcKey(){
        int stationInt,dataTypeInt;
        
        stationInt = 0 << Character.SIZE;
        key =  calcIntVal(station) + calcIntVal(datatype) + calcIntVal(attributes) + value;
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

    
    
}
