package pt.iscte_iul.ista.ES_2324_LEI_GC;

import org.tc33.jheatchart.HeatChart;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HeatmapExample extends JFrame {

    public HeatmapExample(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create some dummy data.
        double[][] data = new double[][]{ {2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7},
        	{2,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7,3,4,5,6,7}};
        
        // Create our heat chart using our data.
        HeatChart chart = new HeatChart(data);
        
        // Customize the chart.
        chart.setTitle("This is my Shart heatmap");
        chart.setXAxisLabel("X Axis");
        chart.setYAxisLabel("Y Axis");
        
        // Render the chart to a BufferedImage.
        BufferedImage image = (BufferedImage) chart.getChartImage();
        
        // Create a JPanel to display the BufferedImage.
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        
        // Add the panel to the frame.
        getContentPane().add(panel, BorderLayout.CENTER);
        
        // Set frame size and make it visible.
        setSize(1500, 1200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HeatmapExample("Heatmap Example");
    }
}
