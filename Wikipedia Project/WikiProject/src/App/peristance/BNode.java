/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.peristance;

import java.io.Serializable;

/**
 * Nodes for the Btree class
 * @author chrisrk192
 */
public class BNode  implements Serializable{
    int MAXKEYS = 32;
    int numkeys;//count
    long parent;
    boolean isLeaf;
    boolean isRoot;
    
    String [] keys;
    long [] values; //RAF positions, so these are technically keys to data sets
    BNode children[];

    public BNode() {
        keys = new String[MAXKEYS];
        children = new BNode[MAXKEYS];
        values = new long[MAXKEYS];
    }
    
    
}
