
package assignment1;
/**
 *
 * @author Christopher King
 */

import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;
import java.net.*;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Assignment1 {
    public static void main(String[] args) {
        String baseURL = "https://www.ncdc.noaa.gov/cdo-web/api/v2/datatypes";//"https://www.ncdc.noaa.gov/cdo-web/api/v2/data";
                //+ "datasetid=GHCND&locationid=ZIP:28801&startdate=2010-05-01&enddate=2010-05-01";      
        String dataParams = "";
                
        //testing////////////////////////////////////////////////////////////////////////        
        String theURL = "https://www.ncdc.noaa.gov/cdo-web/api/v2/data"///data
                //+ "/datatypes";
                + "?datasetid=GSOM&locationid=ZIP:28801&startdate=2016-05-01&enddate=2016-06-01&limit=1000";      
        System.out.println(GetJSON(GetConnection(baseURL)));
//HashMap map = FilterJSON(GetJSON(GetConnection(baseURL)),64 << 2);
        
        //System.out.println(map.PrintMap());
        //testing////////////////////////////////////////////////////////////////////////        
        
    }
    
    
    
    
    
    
    public static String GetJSON(HttpURLConnection myConn){
        String theJSON = "";
        try{
         BufferedReader in = new BufferedReader(
                    new InputStreamReader(myConn.getInputStream()));
            String inputLine;
            StringBuilder inputBuilder = new StringBuilder("");
            while((inputLine = in.readLine()) != null){
                //System.out.println(input);
                inputBuilder.append(inputLine+"\n");
            }
            return inputBuilder.toString();
        }catch(MalformedURLException e){
            System.err.println(" url");
        }catch(IOException e){
            System.err.println(" connection");
            e.printStackTrace();
        }
        return "";
                
    }
    
    public static HttpURLConnection GetConnection(String theURL){
        String token = "IklmwxLbEGHjEsdOtbbqatZIRDdCRHzG";
        
        try{
            System.out.println("Attempting Connection...");
            URL myURL = new URL(theURL);
            HttpURLConnection myConn = (HttpURLConnection)myURL.openConnection();
            myConn.setRequestMethod("GET");
            myConn.setRequestProperty("token", token);
           
            //System.out.println(myConn.toString());
            myConn.connect();
            System.out.println("Connection Successful.");
            return myConn;
            /*
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(myConn.getInputStream()));
            String input;
            while((input = in.readLine()) != null){
                System.out.println(input);
            }
            */   
        }catch(MalformedURLException e){
            System.err.println(" url");
        }catch(IOException e){
            System.err.println(" connection");
            e.printStackTrace();
        }
        return null;
    }
    
    public static HashMap FilterJSON(String theJSON, int size){
        System.out.println(theJSON);
        HashMap map = new HashMap(size);
        JSONObject obj = new JSONObject(theJSON);
        JSONArray data = obj.getJSONArray("results");
        int length = data.length();
        for(int i = 0; i < length; i++){
            //build stuff
            JSONObject element = data.getJSONObject(i);
            
            System.out.println(element.getString("date"));
            System.out.println(element.getString("datatype"));
            System.out.println(element.getString("station"));
            System.out.println(element.getString("attributes"));
            System.out.println(element.getInt("value"));
            System.out.println("\n");
//going to need changing//////////////////////////////////////////////////////////////////////////////////////////            
            Element e = new Element();
            e.setDate(element.getString("date"));
            e.setDatatype(element.getString("datatype"));
            e.setStation(element.getString("station"));
            e.setAttributes(element.getString("attributes"));
            e.setValue(element.getInt("value"));
            e.calcKey();
            map.add(e);
            
        }
        return map;
    }
    
    
}
