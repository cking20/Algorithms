/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.GUI;

/**
 *
 * @author chrisrk192
 */

import java.awt.Image;
import java.awt.Point;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;



public class GraphSceneImpl extends GraphScene<String, String>{
     private static final Border BORDER_4 = BorderFactory.createLineBorder (4);
    private static final Image IMAGE_WIKI =ImageUtilities.loadImage("test/resources/wiki.png");//.loadImage ("./resources/wiki.png"); // NOI18N
    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;
    private LayerWidget interactionLayer;

    public GraphSceneImpl() {
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);
        
        
        //Testing initial Nodes
        /*
        Widget w1 = addNode("1. Hammer");
        w1.setPreferredLocation(new Point(10, 100));
        Widget w2 = addNode("2. Saw");
        w2.setPreferredLocation(new Point(100, 250));
        Widget w3 = addNode("Nail");
        w3.setPreferredLocation(new Point(250, 250));
        Widget w4 = addNode("Bolt");
        w4.setPreferredLocation(new Point(250, 350));
        */
        
        //add zoomability
        getActions().addAction(ActionFactory.createZoomAction());
    }
    
    
    
    /**
     * what will happen when a new Widget is created
     * is triggered whenever addNode is called on the scene
     * @param arg Label of new widget
     * @return 
     */
    @Override
    protected Widget attachNodeWidget(String arg) {
        LabelWidget widget = new LabelWidget(this); 
        //widget.setImage(IMAGE_WIKI);
        
        //make it connect
        widget.getActions().addAction(
            ActionFactory.createExtendedConnectAction(
            connectionLayer, new MyConnectProvider()));
        //makes it move
         widget.getActions().addAction(
            ActionFactory.createAlignWithMoveAction(
            mainLayer, interactionLayer,
            ActionFactory.createDefaultAlignWithMoveDecorator()));
        widget.setBorder(BORDER_4); 
        widget.setLabel(arg);
        mainLayer.addChild(widget);
        
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget (String edge) {
        ConnectionWidget connection = new ConnectionWidget (this);
        connection.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);
        connectionLayer.addChild (connection);
        return connection;
    }
    @Override
    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
        Widget w = sourceNode != null ? findWidget (sourceNode) : null;
        ((ConnectionWidget) findWidget (edge)).setSourceAnchor (AnchorFactory.createRectangularAnchor (w));
    }
    @Override
    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
        Widget w = targetNode != null ? findWidget (targetNode) : null;
        ((ConnectionWidget) findWidget (edge)).setTargetAnchor (AnchorFactory.createRectangularAnchor (w));
    }

    
    
    private class MyConnectProvider implements ConnectProvider {
        @Override
        public boolean isSourceWidget(Widget source) {
            return source instanceof LabelWidget && source != null? true : false;
        }
        @Override
        public ConnectorState isTargetWidget(Widget src, Widget trg) {
            return src != trg && trg instanceof LabelWidget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
        }
        @Override
        public boolean hasCustomTargetWidgetResolver(Scene arg0) {
            return false;
        }
        @Override
        public Widget resolveTargetWidget(Scene arg0, Point arg1) {
            return null;
        }
        @Override
        public void createConnection(Widget source, Widget target) {
            ConnectionWidget conn = new ConnectionWidget(GraphSceneImpl.this);
            conn.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
            conn.setTargetAnchor(AnchorFactory.createRectangularAnchor(target));
            conn.setSourceAnchor(AnchorFactory.createRectangularAnchor(source));
            connectionLayer.addChild(conn);
        }

    }
}
