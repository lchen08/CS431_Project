package GUIPackage.DrawingPackage;

import JobPackage.Job;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is a class to draw the Gantt charts with the timestamps and the jobs in the schedule.
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
public class ChartDrawer extends JPanel
{
    private int xStart;
    private int yStart;
    private final int RECT_WIDTH = 70;
    private final int RECT_HEIGHT = 30;
    private final int CHART_SECT_HEIGHT = 50;
    private ArrayList<Rectangle> rectangles;
    ArrayList<Job> schedule;
    int[] completionIndices;

    /** Unused constructor - only for unit testing. */
    public ChartDrawer()
    {
        //starting chart position
        xStart = 5;
        yStart = 5;
        createChart(50);
        setPreferredSize(new Dimension(1000,30));
        repaint();
    }

    /**
     * Constructs a Gantt chart for a given schedule. Intended only for schedules with
     * first-come-first-serve and shortest job first algorithms.
     * @param schedule The schedule of the jobs and how they were processed by the CPU
     */
    public ChartDrawer(ArrayList<Job> schedule)
    {
        this (schedule, null);
    }

    /**
     * Constructs a Gantt chart for a given schedule. Intended only for schedules using round
     * robin algorithms.
     * @param schedule The schedule of the jobs and how they were processed by the CPU
     * @param completionIndices The list of indices marking when jobs finished in the schedule
     */
    public ChartDrawer(ArrayList<Job> schedule, int[] completionIndices)
    {
        this.schedule = schedule;
        this.completionIndices = completionIndices;
        int rectQty = schedule.size();

        //formatting the chart
        xStart = 5;
        yStart = 5;
        int chartWidth = (rectQty+1)*RECT_WIDTH; //enough spacing to show all text
        setPreferredSize(new Dimension(chartWidth,CHART_SECT_HEIGHT));

        //draw the chart
        createChart(rectQty);
        repaint();
    }

    /**
     * Creates the base for the Gantt chart. Uses the quantity of jobs to determine how big the
     * chart needs to be.
     * @param quantity The number of jobs in the schedule
     */
    private void createChart(int quantity)
    {
        rectangles = new ArrayList<>();
        int xPos = xStart; //avoid altering the starting x position by using new variable
        if (quantity > 0)
        {
            for (int count = 0; count < quantity; count++)
            {
                //adds rectangles for the chart to array with specified positions
                rectangles.add(new Rectangle(xPos, yStart, RECT_WIDTH, RECT_HEIGHT));
                xPos += RECT_WIDTH;
            }
        }
        //create a blank chart if quantity is 0
        else
            rectangles.add(new Rectangle(0,0,0,0));
    }

    /**
     * Draws the text to detail the Gantt chart. Adds the job labels and time stamp.
     * @param g The drawer
     */
    private void drawText(Graphics g)
    {
        int bufferXSpace = 13;
        int bufferYSpace = 15;
        int numSpacing = RECT_WIDTH - 1;
        int numXPosition = xStart - 1;
        int numYPosition = yStart + RECT_HEIGHT + bufferYSpace;
        int jobXPosition = xStart + bufferXSpace;
        int jobYPosition = yStart + RECT_HEIGHT/ 2;

        g.drawString("0", numXPosition,numYPosition); //adds time 0 to chart

        //draws job labels and timestamps for first come first serve and shortest job first
        if (completionIndices == null)
        {
            for (Job job : schedule)
            {
                g.drawString(job.getName(), jobXPosition, jobYPosition);
                jobXPosition += RECT_WIDTH;
                numXPosition += RECT_WIDTH;
                g.drawString(Integer.toString(job.getTime()), numXPosition, numYPosition);
            }
        }
        //draws job labels and timestamps for round robin scheduler
        else
        {
            int completionIndexPtr = completionIndices.length - 1;
            for (int i = 0; i < schedule.size(); i++)
            {
                Job job = schedule.get(i);
                String marker;

                //the index matches the pointer marking that a job completed - add visual marker
                if (completionIndexPtr != -1 && i == completionIndices[completionIndexPtr])
                {
                    g.setColor(Color.BLUE);
                    completionIndexPtr--;
                    marker = "*";
                }
                else
                {
                    g.setColor(Color.BLACK);
                    marker = "";
                }
                g.drawString(job.getName() + marker, jobXPosition, jobYPosition);
                jobXPosition += RECT_WIDTH;
                numXPosition += RECT_WIDTH;
                g.drawString(Integer.toString(job.getTime()) + marker, numXPosition,
                        numYPosition);
            }
        }
    }
    /**
     * Repaints the Gantt chart and its associated text.
     * @param g To create drawings
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //loops through all the rectangles in the array list to draw Gantt chart
        for (Rectangle rectangle : rectangles)
            rectangle.drawRectangle(g);

        drawText(g);
    }
}
