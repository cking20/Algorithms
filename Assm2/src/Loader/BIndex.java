/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loader;
import java.io.Serializable;

/**
 *
 * @author chrisrk192
 */
public class BIndex implements Serializable{
    private BNode node;
    private int index;

    public BIndex(BNode n, int i) {
        this.node = n;
        this.index = i;
    }

    /**
     * @return the node
     */
    public BNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(BNode node) {
        this.node = node;
    }

    /**
     * @return the index of the Bnode's key array
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
