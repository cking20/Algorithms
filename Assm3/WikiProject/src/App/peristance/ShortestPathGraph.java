/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.peristance;
import java.io.Serializable;
/**
 *
 * @author chrisrk192
 */
public class ShortestPathGraph implements Serializable{
    public String title;
    public double timeCalced;
    public double[] dist;// = new double[f.length];
    public int[] pred;//= new int[f.length];
    public boolean[] visited;// = new boolean[f.length];

    public ShortestPathGraph(String t, int d) {
        title = t;
        timeCalced = System.currentTimeMillis();
        dist = new double[d];
        pred = new int[d];
        visited = new boolean[d];
    }
    
    
}
