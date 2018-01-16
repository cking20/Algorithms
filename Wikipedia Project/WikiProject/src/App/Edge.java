/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.Serializable;

/**
 *
 * @author chrisrk192
 */
public class Edge implements Serializable{
    int srcID;
    int destID;
    String src;
    String dest;
    Double weight;

    public Edge(String dest, String src, int destID, int sourceID) {
        this.destID = destID;
        this.srcID = sourceID;
        this.dest = dest;
        this.src = src;
        this.weight = 0.0;
    }
    
    
}
