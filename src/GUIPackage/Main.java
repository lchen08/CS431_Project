package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is the class to run the frame containing the interface for the CPU scheduler. The frame
 * has a file chooser panel and the chart panel where the Gantt charts would appear once a job
 * file is submitted for calculation.
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
public class Main extends JFrame
{
    private static final int FRAME_WIDTH = 1400;
    private static final int FRAME_HEIGHT = 800;
    private static final String FRAME_TITLE = "CS 431: CPU Scheduler Project";
    private static JPanel fileReqPanel;
    private static JPanel chartsPanel;

    /** Main - runs frame for the entire program. */
    public static void main(String[] args) { new Main(); }

    /**Constructs the main frame for the program with prompting the user for the job file. */
    public Main()
    {
        BorderLayout borderLayout = new BorderLayout();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centers the frame to the screen
        setLayout(borderLayout);

        //creates and adds the panels
        fileReqPanel = new FileRequestPanel(this);
        chartsPanel = new ChartsSectionPanel();
        add(fileReqPanel, borderLayout.NORTH);
        add(chartsPanel, borderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Updates the chart panel area for Gantt charts for the CPU schedulers.
     * @param jobFile The job file with data to update the Gantt charts
     * @throws FileNotFoundException If the job file given does not exist
     */
    protected void updateCharts(File jobFile) throws FileNotFoundException
    {
        //removes, updates, and re-adds the panel depending on job file given
        remove(chartsPanel);
        chartsPanel = new ChartsSectionPanel(jobFile);
        add(chartsPanel);

        //updates the frame for changes
        revalidate();
        repaint();
    }
}