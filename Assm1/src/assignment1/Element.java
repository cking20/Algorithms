package assignment1;

/**
 *
 * @author chrisrk192
 */
public class Element {
    private Value data;
    private Element next;
    
    
    /**
     * Sets the data to newItem
     * 
     * @param newItem the data to be set
    */ 
    public void setData(Value newItem) {
        if(newItem == null)
            data = null;
        else
            data = newItem.Clone( );
    }
    
    /**
     * Sets the next object in the list
     * 
     * @param newNext the next object
    */ 
    public void setNext(Element newNext) {
        next = newNext;
    }
    
    /**
     * @return a shallow copy of the data
    */ 
    public Value getData( ) {
        return data;
    }
    
    /**
     * @return a shallow copy of the next item
    */ 
    public Element getNext( ) {
        return next;
    }
    
}
