/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbtree;

/**
 *
 * @author chrisrk192
 */
public class NodeType {
    private boolean isRed;
    private Element data;
    private NodeType left, right, parent;

    public NodeType() {
        isRed = true;
    }
    
    public void SetData(Element nData) {
        if(nData != null)
            data = nData;
        else
            data = null;
    }
    
    public Value GetData( ) {
        return data.getData().Clone( );
    }
    
    public void SetLeft(NodeType nLeft) {
        left = nLeft;
    }
    
    public void SetRight(NodeType nRight) {
        right = nRight;
    }
    
    public NodeType GetLeft( ) {
        return left;
    }
    
    public NodeType GetRight( ) {
        return right;
    }
    public boolean IsRed(){
        return isRed;
    }
    public void SetRed(boolean isIt){
        isRed = isIt;
    }

    /**
     * @return the parent
     */
    public NodeType getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(NodeType parent) {
        this.parent = parent;
    }
    
}
