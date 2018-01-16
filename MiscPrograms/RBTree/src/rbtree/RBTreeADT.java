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
public class RBTreeADT {
    NodeType theRoot;
    /**
     * Constructor
     */
    public void RBTreeADt(){
        theRoot = null;
    }
    
    public void Add(Element newItem) {
        NodeType temp;
        if(theRoot == null) {
            //Add a root
            temp = new NodeType( );
            temp.SetRed(false);//red by default need black for root
            temp.SetData(newItem.Clone( ));
            theRoot = temp;
        } else if(theRoot.GetData( ).getKeyString().compareTo(newItem.getData( ).getKeyString( )) < 0)
            theRoot.SetRight(AddNode(theRoot, theRoot.GetRight( ), newItem));
        else
            theRoot.SetLeft(AddNode(theRoot, theRoot.GetLeft( ), newItem));
    }
    
    private NodeType AddNode(NodeType parent, NodeType aRoot, Element newItem) {
        NodeType temp;
        if(aRoot == null) {
            //Add a node
            temp = new NodeType( );//red
            temp.SetData(newItem.Clone( ));
            temp.setParent(parent);
            aRoot = temp;
            //Make sure in a good configuration
            FixIlligalConfiguration(aRoot);/////////////////////////////////////////WIP/////////////////////////////////////////////////
        } else if(aRoot.GetData( ).getKeyString().compareTo(newItem.getData( ).getKeyString( )) < 0)
            //go right
            aRoot.SetRight(AddNode(aRoot, aRoot.GetRight( ), newItem));
        else // go left
            aRoot.SetLeft(AddNode(aRoot, aRoot.GetLeft( ), newItem));
        
        return aRoot;
    }
    
    public void Remove(String key){
        
    }
    private void Delete(NodeType z){
        NodeType y = z;
        
    }
    private void Transplant(NodeType u, NodeType v){
        if(u.getParent() == null)
            this.theRoot = v;
        else if(u == u.getParent().GetLeft())
            u.getParent().SetLeft(v);
        else
            u.getParent().SetRight(v);
        v.setParent(u.getParent());
    }
    public Value Retrieve(String key){ 
        Value result;
        if(theRoot == null)
            result = null;
        else if(theRoot.GetData( ).getKeyString().compareTo(key) == 0)
                result = theRoot.GetData();
        else if(theRoot.GetData( ).getKeyString().compareTo(key) > 0)
                result = RetrieveNode(key, theRoot.GetLeft( ));
        else
                result = RetrieveNode(key, theRoot.GetRight( ));
        
        return result;
    }
    private Value RetrieveNode(String key, NodeType aRoot){
        Value result;
        if(aRoot == null)
            result = null;
        else if(aRoot.GetData( ).getKeyString().compareTo(key) == 0)
                result = aRoot.GetData( ).Clone( );
        else if(aRoot.GetData( ).getKeyString().compareTo(key) > 0)
                result = RetrieveNode(key, aRoot.GetLeft( ));
        else
                result = RetrieveNode(key, aRoot.GetRight( ));
        return result;
    }
    
    private void FixIlligalConfiguration(NodeType current){
        boolean parentIsRed, grandParentIsRed;
        NodeType uncle;
        
        //current is root: black
        if(current.getParent() == null){
            current.SetRed(false);
            return;
        }
        //cases new node(red)
        if(current.IsRed()){
            //try to get uncle
            try {
                if(current.getParent().getParent().GetLeft() == current.getParent())
                    uncle = current.getParent().getParent().GetRight();
                else
                    uncle =current.getParent().getParent().GetLeft();
            } catch (NullPointerException e) {
                uncle = null;
            }
            
            //there is an uncle
            if(uncle != null){
                //parent and uncle are red: push blackness down from grandparent
                if(current.getParent().IsRed() && uncle.IsRed()){
                    uncle.getParent().SetRed(true);
                    uncle.SetRed(false);
                    current.getParent().SetRed(false);
                    if(uncle.getParent().getParent() ==null)//root
                        uncle.getParent().SetRed(false);
                }
            }
            
            
        }
        
    }
    
    private void SwapRightRight(NodeType cur, NodeType par, NodeType grandPar){
        NodeType y = par.GetRight();
        par.setParent(grandPar.getParent());
        grandPar.setParent(par);
        par.SetRight(grandPar);
        grandPar.SetLeft(y);
        //color checks
        par.SetRed(grandPar.IsRed());
        cur.SetRed(false);
        grandPar.SetRed(false);
        if(par.getParent() != null)
            par.SetRed(true);
        else//is root now
            par.SetRed(false);
    }
    
    private void SwapRightLeft(NodeType cur, NodeType par, NodeType grandPar){
    //Precondition: grandparent.getleft == parent, parent.getRight == cur
        NodeType temp = par.GetLeft();
        par.SetLeft(par.GetRight());
        par.SetRight(temp);
        SwapRightRight(cur,par,grandPar);
    }
    
    private void SwapLeftLeft(NodeType cur, NodeType par, NodeType grandPar){
        NodeType y = par.GetLeft();
        par.setParent(grandPar.getParent());
        grandPar.setParent(par);
        par.SetLeft(grandPar);
        grandPar.SetRight(y);
        //color checks
        par.SetRed(grandPar.IsRed());
        cur.SetRed(false);
        grandPar.SetRed(false);
        if(par.getParent() != null)
            par.SetRed(true);
        else//is root now
            par.SetRed(false);
    }
    
    private void SwapLeftRight(NodeType cur, NodeType par, NodeType grandPar){
    //Precondition: grandparent.getleft == parent, parent.getRight == cur
        NodeType temp = par.GetLeft();
        par.SetLeft(par.GetRight());
        par.SetRight(temp);
        SwapLeftLeft(cur,par,grandPar);
    }
    
}
