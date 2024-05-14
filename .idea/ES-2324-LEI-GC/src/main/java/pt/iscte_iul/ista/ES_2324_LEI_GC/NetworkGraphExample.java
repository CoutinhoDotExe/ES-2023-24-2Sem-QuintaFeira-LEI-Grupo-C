package pt.iscte_iul.ista.ES_2324_LEI_GC;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import javax.swing.*;

public class NetworkGraphExample {

    public static void main(String[] args) {
        // Create a graph
        Graph<Integer, String> graph = new SparseGraph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge("Edge1", 1, 2);
        graph.addEdge("Edge2", 2, 3);
        graph.addEdge("Edge3", 5, 3);

        // Create a visualization component for the graph
        Layout<Integer, String> layout = new CircleLayout<>(graph);
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(layout);
        vv.setPreferredSize(new java.awt.Dimension(1500, 800));

        // Create a frame to hold the visualization component
        JFrame frame = new JFrame("Network Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}