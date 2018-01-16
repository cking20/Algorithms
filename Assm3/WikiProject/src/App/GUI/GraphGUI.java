/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App.GUI;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.netbeans.api.visual.graph.GraphScene;

/**
 *
 * @author chrisrk192
 */
public class GraphGUI extends JPanel{
    public GraphScene scene;
    
    public GraphGUI() {
        initComponents();
    }
    private void initComponents() {
        //Set the layout:
        setLayout(new BorderLayout());
        //Create a JScrollPane:
        JScrollPane scrollPane = new JScrollPane();
        //Add the JScrollPane to the JPanel:
        add(scrollPane, BorderLayout.CENTER);
        //Create the GraphSceneImpl:
        scene = new GraphSceneImpl();
        //Add it to the JScrollPane:
        scrollPane.setViewportView(scene.createView());
        //Add the SatellitView to the scene:
        add(scene.createSatelliteView(), BorderLayout.WEST);
    }
    
    public static void createConnection (GraphScene scene, String edgeID, String nodeID1, String nodeID2) {
        scene.addEdge (edgeID);
        scene.setEdgeSource (edgeID, nodeID1);
        scene.setEdgeTarget (edgeID, nodeID2);
    }
    
}
