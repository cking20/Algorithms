/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author chrisrk192
 */
public class DataCollection {
    
    /**
     * @return the names of all downloaded pages
     */
    public static String[] GetDownloadedPageTitles(){
        File dir = new File(App.peristance.Persist.getDir());
        File [] pages = dir.listFiles();
        int count = pages.length;
        String [] s = new String[count];
        for(int i = 0; i < count; i++){
            s[i] = pages[i].getName().replace(".ser", "");
        }
        return s;
    }
    
    /**
     * Downloads a page using the Wikipedia API. Includes the title, content, and links to other pages
     * @param title
     * @return 
     */
    public static Page downloadPage(String title) {
        String checkedTitle = checkTitle(title);
        String baseURL = "https://en.wikipedia.org/w/api.php?format=json&action=query";
        //Page p = new Page();
        String pageURL = baseURL + "&prop=extracts&explaintext=&titles=" + checkedTitle;
        //String linkURL = baseURL + "&prop=links&pllimit=500&titles=" + checkedTitle;
        Page p = FilterSinglePageJSON(GetJSON(GetConnection(pageURL)));
        if(p != null){
            String linkURL = baseURL + "&pageids="+p.ID+"&generator=links&gpllimit=max";
            //http://en.wikipedia.org/w/api.php?action=query&format=json        &pageids=       12610483        &generator=links&gpllimit=max
            p.links = FilterLinkJSON(GetJSON(GetConnection(linkURL)),p.ID, p.title);
            p.TimeDownloaded = System.currentTimeMillis();
            
        }
        return p;
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
            //System.err.println(" connection");
        }
        return "";    
    }
    
    /**
     * Connects to the provided url
     * @param theURL to conect to
     * @return the connection
     */
    public static HttpURLConnection GetConnection(String theURL){
        //String token = "IklmwxLbEGHjEsdOtbbqatZIRDdCRHzG";
        URL myURL; 
        HttpURLConnection myConn;
        
        try{
            myURL = new URL(theURL);
            myConn = (HttpURLConnection)myURL.openConnection();
            myConn.setRequestMethod("GET");
            //myConn.setRequestProperty("token", token);
            myConn.connect();
            return myConn;  
        }catch(MalformedURLException e){
            System.err.println(" url");
        }catch(IOException e){
            
            System.err.println("IOEXCEPTION");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Parses through the provided string containing JSON and creates values based
     *      on the data found there. Then adds these values to the list of edges
     * @param theJSON String format of JSON, requires "results" string for array
     * @param srcID
     * @param srctitle
     * @return 
     */
    public static ArrayList<Edge> FilterLinkJSON(String theJSON, int srcID, String srctitle){
        //System.out.println("App.DataCollection.FilterLinkJSON()");
        JSONObject obj,query,page,temp;
        JSONObject element;
        JSONArray links;
        int length;
        String tempTitle; int tempID;
        ArrayList<Edge> ar = new ArrayList<>();
        
        //System.out.println(theJSON);
        try{
            //System.out.println(theJSON);
            obj = new JSONObject(theJSON);
            query = obj.getJSONObject("query");
            //System.out.println(query.toString());
            page = query.getJSONObject("pages");
            //System.out.println(page.toString());
            //tempA = page.getJSONArray("21721040");
            //temp = page.getJSONObject(""+srcID);
            //System.out.println("\n");
            //System.out.println(temp.toString());
            JSONArray prop = page.names();
            for(int i = 0; i < prop.length();i++){
                temp = page.getJSONObject((String)prop.get(i));
                //System.out.println("\n");
                //System.out.println(temp.toString());
                //data = page.getJSONObject((String)prop.get(i));

                try{
                    
                    tempTitle = temp.getString("title");
                    tempID = temp.getInt("pageid");
                    if(temp.getInt("ns") == 0)
                        ar.add(new Edge(tempTitle,srctitle,tempID,srcID));
                    //p.ID = temp.getInt("pageid");
                    //p.title = temp.getString("title");
                    //p.text = data.getString("extract");
                }catch(JSONException e){
                    //System.err.println("JSONException\n");
                    //System.out.println(theJSON);
                    //e.printStackTrace();
                    //p = null; 
                }
            }
            //links = temp.getJSONArray("links");
            //System.out.println(links.toString());
            //length = links.length();
            //for(int i = 0; i < length; i++){               
                //populate value
               // element = links.getJSONObject(i);
                
                /* OLD WRONG STUFF
                try {
                    Page tempPage = FilterPageIDJSON(GetJSON(GetConnection(
                        "https://en.wikipedia.org/w/api.php?&format=json&action=query&titles="
                                +checkTitle(element.getString("title")))));
                    if(tempPage != null){
                        ar.add(new Edge(element.getString("title"),srctitle,tempPage.ID,srcID));
                        //System.out.println("Link from " + srcID + " to "+ tempPage.ID);
                    }
                } catch (Exception e) {
                    //System.err.println("Link had bad name\n");
                }
                    */
            //}
        } catch(JSONException e){
            System.err.println("NO RESULTS\n");
        }      
        return ar;
    }
    
    /**
     * Parses through the provided string containing JSON and creates a new page based
     *      on the data found there.
     * @param theJSON String format of JSON, requires "results" string for array
     * @return 
     */
    public static Page FilterSinglePageJSON(String theJSON){
        //System.out.println("App.DataCollection.FilterSinglePageJSON()");
        JSONObject obj,query,page,temp;
        JSONObject element;
        JSONObject data;
        int length;
        Page p = new Page();
        
        try{
            //System.out.println(theJSON);
            obj = new JSONObject(theJSON);
            query = obj.getJSONObject("query");
            //System.out.println(query.toString());
            page = query.getJSONObject("pages");
            //System.out.println(page.toString());
            //tempA = page.getJSONArray("21721040");
            JSONArray prop = page.names();
           // System.out.println("\n\nIMPORTANT HERE: "+prop.toString()+'\n');
            temp = page.getJSONObject((String)prop.get(0));
            //System.out.println("\n");
            //System.out.println(temp.toString());
            data = page.getJSONObject((String)prop.get(0));
            
            try{
                p.ID = data.getInt("pageid");
                p.title = data.getString("title");
                p.text = data.getString("extract");
            }catch(JSONException e){
                //System.err.println("JSONException\n");
                //System.out.println(theJSON);
                //e.printStackTrace();
                p = null; 
            }
            //}
        } catch(JSONException e){
            //System.err.println("JSONException\n");
            //System.out.println(theJSON);
            //e.printStackTrace();
            p = null;
        }      
        return p;
    }
    
    /**
     * Obsolete, DO NOT USE, the faster method is FilterSinglePageJSON
     * @param theJSON
     * @return 
     */
    public static Page FilterPageIDJSON(String theJSON){
        System.err.println("App.DataCollection.FilterPageIDJSON() DO NOT USE");
        JSONObject obj,query,page,temp;
        JSONObject element;
        JSONObject data;
        int length;
        Page p = new Page();
        
        try{
            //System.out.println(theJSON);
            obj = new JSONObject(theJSON);
            query = obj.getJSONObject("query");
            //System.out.println(query.toString());
            page = query.getJSONObject("pages");
            //System.out.println(page.toString());
            //tempA = page.getJSONArray("21721040");
            JSONArray prop = page.names();
           // System.out.println("\n\nIMPORTANT HERE: "+prop.toString()+'\n');
            temp = page.getJSONObject((String)prop.get(0));
            //System.out.println("\n");
            //System.out.println(temp.toString());
            data = page.getJSONObject((String)prop.get(0));
            
            try{
                p.ID = data.getInt("pageid");
                p.title = data.getString("title");
                //p.text = data.getString("extract");
            }catch(JSONException e){
                //System.err.println("JSONException\n");
                //System.out.println(theJSON);
                //e.printStackTrace();
                p = null; 
            }
            //}
        } catch(JSONException e){
            //System.err.println("JSONException\n");
            //System.out.println(theJSON);
            //e.printStackTrace();
            p = null;
        }      
        return p;
    }
       
    /**
     * Verifies that the String s is an acceptable title
     * @param s
     * @return 
     */
    private static String checkTitle(String s){
        String r = s;
        String[] sa = r.split(" ");
        r = "";
        for(int i = 0; i < sa.length-1; i++)
            r += sa[i]+"%20";
        r += sa[sa.length-1];
        return r;
    }
}
