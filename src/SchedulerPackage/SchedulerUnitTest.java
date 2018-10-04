package SchedulerPackage;

import JobPackage.Job;
import JobPackage.JobQueuer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is to test and verify the scheduler algorithms for the schedulers in the package.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public class SchedulerUnitTest
{
    public static void main(String[] args) throws FileNotFoundException
    {
        File file1 = new File("testdata1.txt");
        File file2 = new File("testdata2.txt");
        File file3 = new File("testdata3.txt");

        JobQueuer jq = new JobQueuer(file3);

        //First come first serve scheduler
        FCFSScheduler scheduler1 = new FCFSScheduler(jq.getQueue(), jq.getQueueSize());
        System.out.println("File 3 - FCFS");
        ArrayList<Job> schedule1 = scheduler1.getSchedule();
        for (Job job : schedule1)
            System.out.println(job.getName() + " " + job.getTime());

        System.out.println("\nAverage completion time: " + scheduler1.getAverageTime() + " ms\n");

        //Shortest Job First scheduler
        SJFScheduler scheduler2 = new SJFScheduler(jq.getQueue(), jq.getQueueSize());
        System.out.println("File 3 - SJF");
        ArrayList<Job> schedule2 = scheduler2.getSchedule();
        for (Job job : schedule2)
            System.out.println(job.getName() + " " + job.getTime());

        System.out.println("\nAverage completion time: " + scheduler2.getAverageTime() + " ms\n");

        //Round Robin Time Slice 2 scheduler
        RRScheduler scheduler3 = new RRScheduler(jq.getQueue(), jq.getQueueSize(), 2);
        System.out.println("File 3 - RR-2");
        ArrayList<Job> schedule3 = scheduler3.getSchedule();
        for (Job job : schedule3)
            System.out.println(job.getName() + " " + job.getTime());

        int[] completionIndices = scheduler3.getCompletionIndices();
        System.out.print("Completion indices: ");
        for (int index : completionIndices)
            System.out.print(index + " ");

        System.out.println("\nAverage completion time: " + scheduler3.getAverageTime() + " ms");

        //Round Robin Time Slice 5 scheduler
        RRScheduler scheduler4 = new RRScheduler(jq.getQueue(), jq.getQueueSize(), 5);
        System.out.println("File 3 - RR-5");
        ArrayList<Job> schedule4 = scheduler4.getSchedule();
        for (Job job : schedule4)
            System.out.println(job.getName() + " " + job.getTime());

        int[] completionIndices2 = scheduler4.getCompletionIndices();
        System.out.print("Completion indices: ");
        for (int index : completionIndices2)
            System.out.print(index + " ");

        System.out.println("\nAverage completion time: " + scheduler4.getAverageTime() + " ms");
    }
}
