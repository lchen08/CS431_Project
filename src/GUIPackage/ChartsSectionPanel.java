package GUIPackage;

import GUIPackage.DrawingPackage.ChartDrawer;
import JobPackage.*;
import SchedulerPackage.FCFSScheduler;
import SchedulerPackage.RRScheduler;
import SchedulerPackage.SJFScheduler;
import SchedulerPackage.SchedulerInterface;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is the panel for the Gantt charts results section. This displays: which CPU scheduler
 * algorithm is being used to create the chart, the Gantt charts, and the average completion time
 * for the jobs.
 *
 * @author Lisa Chen
 * @since July 17, 2018
 */
public class ChartsSectionPanel extends JPanel
{
    private static final int PANEL_WIDTH = 1000;
    private static final int PANEL_HEIGHT = 140;

    /** Constructs a default blank panel. */
    public ChartsSectionPanel() { }

    /**
     * Constructs the panel with all the chart components, where the information is populated
     * from a given file.
     * @param jobFile The file with the job information to populate the Gantt chart
     * @throws FileNotFoundException
     */
    public ChartsSectionPanel(File jobFile) throws FileNotFoundException
    {
        JobQueuer queuer = new JobQueuer(jobFile);
        Job[] queue = queuer.getQueue();
        int queueSize = queuer.getQueueSize();
        int schedulersQty = 4;

        //creating and adding the schedulers and their chart scroll panel sections
        FCFSScheduler fcfsScheduler = new FCFSScheduler(queue, queueSize);
        SJFScheduler sjfsScheduler = new SJFScheduler(queue, queueSize);
        RRScheduler rr2Scheduler = new RRScheduler(queue, queueSize, 2);
        RRScheduler rr5Scheduler = new RRScheduler(queue, queueSize, 5);
        JScrollPane[] panels = new JScrollPane[schedulersQty];
        panels[0] = new JScrollPane(new ChartPanel(fcfsScheduler),
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panels[1] = new JScrollPane(new ChartPanel(sjfsScheduler),
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panels[2] = new JScrollPane(new ChartPanel(rr2Scheduler),
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panels[3] = new JScrollPane(new ChartPanel(rr5Scheduler),
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        for (JScrollPane panel : panels)
        {
            panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            add(panel);
        }
    }

    /**
     * This is the class for the inner panel to contain the Gantt chart and related information.
     */
    class ChartPanel extends JPanel
    {
        private final String FCFS_TEXT = "First Come First Serve Algorithm";
        private final String SJF_TEXT = "Shortest Job First Algorithm";
        private final String RR_TEXT = "Round Robin Algorithm ";

        /**
         * Constructs the inner chart panel with the Gantt chart and its information. The
         * chart's information is populated from the CPU scheduler's created schedule.
         * @param scheduler The CPU scheduler with the information for the schedule
         */
        public ChartPanel(SchedulerInterface scheduler)
        {
            JLabel header = new JLabel();
            ChartDrawer chart;

            //creates the JLabel header depending on scheduler algorithm type
            if (scheduler instanceof FCFSScheduler)
            {
                header.setText(FCFS_TEXT);
                chart = new ChartDrawer((scheduler.getSchedule()));
            }
            else if (scheduler instanceof SJFScheduler)
            {
            header.setText(SJF_TEXT);
            chart = new ChartDrawer((scheduler.getSchedule()));
            }
            else if (scheduler instanceof RRScheduler)
            {
                String note = "     **Note: Blue means job completion**";
                RRScheduler rrScheduler = (RRScheduler) scheduler;
                header.setText(RR_TEXT + "(Time Slice: " + rrScheduler.getTimeSplit() + ")" +
                        note);
                chart = new ChartDrawer((scheduler.getSchedule()),
                        rrScheduler.getCompletionIndices());
            }
            //when a scheduler interface object does not match the classes expected
            else
                throw new InputMismatchException();

            //if header was successfully created - create rest of objects
            DecimalFormat df = new DecimalFormat("#.00");
            String timeMessage = "Average Completion Time: " + df.format(scheduler.getAverageTime())
                    + " ms";
            JLabel averageTime = new JLabel(timeMessage);
            JPanel chartSection = new JPanel();

            //formatting the panel and its objects
            BorderLayout gridLayout = new BorderLayout();
            setLayout(gridLayout);
            header.setHorizontalAlignment(SwingConstants.CENTER);
            averageTime.setHorizontalAlignment(SwingConstants.CENTER);

            //add everything to panels
            add(header,BorderLayout.NORTH);
            add(averageTime, BorderLayout.SOUTH);
            chartSection.add(chart);
            add(chartSection, BorderLayout.CENTER);
        }
    }
}
