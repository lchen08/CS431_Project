package GUIPackage.DrawingPackage;

import javax.swing.*;
import java.awt.*;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is the unit test for drawing rectangles onto a JPanel. Need to alter ChartDrawer to
 * comment out drawText() in the paintComponent section.
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
public class DrawingUnitTest extends JPanel
{
    public DrawingUnitTest()
    {
        ChartDrawer chart = new ChartDrawer();
        JFrame frame = new JFrame();
        frame.setSize(1400, 800);
        frame.setTitle("Unit Test");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //centers the frame to the screen

        setPreferredSize(new Dimension(1000,800));
        add(chart);
        frame.add(this);

        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        new DrawingUnitTest();
    }
}
