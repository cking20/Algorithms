/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author chrisrk192
 */
public class Page implements Serializable{
    //book keeping
    public long TimeDownloaded;
    public long visited;
    public String parent;
    public double weight;
    //data
    public int ID;
    public String title;
    public String text;
    public ArrayList<Edge> links;
   
    
    
}
