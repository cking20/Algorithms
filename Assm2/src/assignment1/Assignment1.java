package assignment1;
/**
 *
 * @author Christopher King
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Assignment1 {
    
    /**
     * Populates a map with the JSOM from the provided url
     * @param map the map to be populated
     * @param finalURL the url to connect to
     * @param locationIden the Location identifier(State number in NOAA)
     * @param dataType the dataType to populate with
     * @return 
     */
    public static HashMap gather(HashMap map, String finalURL, int locationIden, String dataType) {  
        //gets connection, then gets JSON, then populates map
        FilterJSON(map, GetJSON(GetConnection(finalURL)), locationIden,dataType);
        return map;
    }
    
    /**
     * Pulls the JSON from the connected url into a string.
     * ASSUMES connection is active
     * @param myConn the active connection to url
     * @return the JSON from the website in string format
     */
    public static String GetJSON(HttpURLConnection myConn){
        String theJSON = "";
        String inputLine;
        StringBuilder inputBuilder;
        BufferedReader in;
        
        try{
            in = new BufferedReader(new InputStreamReader(myConn.getInputStream()));
            inputBuilder = new StringBuilder("");
            while((inputLine = in.readLine()) != null){
                inputBuilder.append(inputLine+"\n");
            }
            return inputBuilder.toString();   
        }catch(MalformedURLException e){
            System.err.println(" url");
        }catch(IOException e){
            System.err.println(" connection");
        }
        return "";    
    }
    
    /**
     * Connects to the provided url
     * @param theURL to conect to
     * @return the connection
     */
    public static HttpURLConnection GetConnection(String theURL){
        String token = "IklmwxLbEGHjEsdOtbbqatZIRDdCRHzG";
        URL myURL; 
        HttpURLConnection myConn;
        
        try{
            myURL = new URL(theURL);
            myConn = (HttpURLConnection)myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.setRequestProperty("token", token);
            myConn.connect();
            return myConn;  
        }catch(MalformedURLException e){
            System.err.println(" url");
        }catch(IOException e){
            System.err.println(" connection");
        }
        return null;
    }
    
    /**
     * Parses through the provided string containing JSON and creates values based
     *      on the data found there. Then adds these values to the map
     * @param map hashmap to populate
     * @param theJSON String format of JSON, requires "results" string for array
     * @param locationIden the State integer from NOAA
     * @return 
     */
    public static HashMap FilterJSON(HashMap map, String theJSON, int locationIden, String typeOfVal){
        JSONObject obj;
        JSONObject element;
        JSONArray data;
        int length;
        Value v;
        
        try{
            //System.out.println(theJSON);
            obj = new JSONObject(theJSON);
            data = obj.getJSONArray("results");
            length = data.length();
            for(int i = 0; i < length; i++){
                               
                //populate value
                element = data.getJSONObject(i);
                                
                v = new Value();
                v.setLocationVal(locationIden);
                v.setDate(element.getString("date"));
                v.setDatatype(element.getString("datatype"));
                v.setStation(element.getString("station"));
                v.setAttributes(element.getString("attributes"));
                v.setValue(element.getInt("value"));
                v.calcKey();
                //only add specidied values to the maps
                if((v.getAttributes().compareTo(",,N,") == 0 || v.getAttributes().compareTo(",,7,0700") == 0) && v.getDatatype().compareTo(typeOfVal) == 0){
                    //System.out.print(i + " ");
                    //System.out.print(v.getDatatype());
                    //System.out.print(v.getAttributes());
                    //System.out.println("           " + v.getValue());
                    map.add(v);
                }
            }
            map.StateID = locationIden;
        } catch(JSONException e){
            System.err.println("NO RESULTS\n");
        }      
        return map;
    }
    
    /**
     * Similarity Metric. Uses a linear regression algorithm to calculate the 
     *      similarity between the values of two maps.
     *      Pros:   Creates a value between 0 and 1
     *              
     * @param base the main map that values are compared to
     * @param compareTo the values being compared
     * @return regression value
     */
    public static double LinRegres(HashMap base, HashMap compareTo){ 
        double r = 0;
        double sumBase = 0;
        double sumCmp = 0;
        double sumProduct = 0;
        Value baseVal = new Value();
        Value cmpVal = new Value();
        boolean bQueued = false;
        boolean cQueued = false;
        
        //reset itterators
        base.Reset();
        compareTo.Reset();
        
        //calculate 
        //sum while both maps have elements left
        while(base.traverse() && compareTo.traverse()){
            baseVal = base.Retrieve();
            cmpVal = compareTo.Retrieve();
            base.map[base.getCurrent()].GetNext();
            compareTo.map[compareTo.getCurrent()].GetNext();
            
            sumProduct += baseVal.getValue() * cmpVal.getValue();
            //square the values and add
            sumBase += Math.pow(baseVal.getValue(),2);
            sumCmp += Math.pow(cmpVal.getValue(),2);
            //System.out.println("ADDING: product sum = "+ sumProduct + " Base sum = "+ sumBase + " Cmp sum ="+sumCmp);
        }
        //product / (sqrt(sumB) * sqrt(sumC))
        r = sumProduct / (Math.sqrt(sumBase) * Math.sqrt(sumCmp));        
        return r;
    } 
    
    public static double AverageHashMapVal(HashMap h ){
        double sum = 0;
        h.Reset();
        Value baseVal;
        while(h.traverse()){
            baseVal = h.Retrieve();
            h.map[h.getCurrent()].GetNext();
            if(baseVal != null )
                sum += baseVal.getValue();  
        }
        double divisor = h.getCount();
        
        sum = sum/divisor;
        return sum;
    }
    
}
