package SchedulerPackage;

import JobPackage.Job;
import java.util.ArrayList;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is a scheduler using the round robin algorithm to create a schedule of jobs
 * for the order and how the jobs are processed by the CPU. The schedule is created by an
 * input job queue with information about the jobs in the queue.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public class RRScheduler implements SchedulerInterface
{
    ArrayList<Job> schedule;
    double averageTime;
    int timeSplit;
    int jobQty;
    int[] completionIndices;

    /**
     * Constructs the first-come-first-serve scheduler using a job queue and its size.
     * @param queue The queue of jobs awaiting CPU processing
     * @param queueSize The number of jobs in the queue
     * @param timeSplit The time amount to split the jobs in the schedule for round robin
     */
    public RRScheduler(Job[] queue, int queueSize, int timeSplit)
    {
        schedule = new ArrayList<>();
        this.timeSplit = timeSplit;
        jobQty = queueSize;
        completionIndices = new int[queueSize];
        Job[] newQueue = copyQueue(queue,queueSize); //avoids altering original input queue

        createSchedule(newQueue, queueSize);
        averageTime = calculateAverageTime();
    }

    /**
     * Copies the jobs in an input job queue to a new queue.
     * @param queue The input queue to be copied
     * @param queueSize The number of elements in the queue
     * @return The resulting copy of the queue
     */
    private Job[] copyQueue(Job[] queue, int queueSize)
    {
        Job[] result = new Job[queueSize];
        for (int i = 0; i < queueSize; i++)
            result[i] = new Job(queue[i].getName(), queue[i].getTime());
        return result;
    }

    /**
     * Craetes the CPU job schedule using first-come-first-serve algorithm.
     * @param queue The queue of jobs awaiting CPU processing
     * @param queueSize THe number of jobs in the queue
     */
    private void createSchedule (Job[] queue, int queueSize)
    {
        int startTime = 0;
        int unfinishedJobQty = queueSize;

        //loops to add jobs to schedule until all jobs are finished processing
        while (unfinishedJobQty > 0)
        {
            for (int i = 0; i < queueSize; i++)
            {
                int remainingTime = queue[i].getTime();
                //when still existing unfinished jobs & current job indexed is unfinished
                if (unfinishedJobQty > 0 && remainingTime > 0)
                {
                    String jobName = queue[i].getName();
                    //only add whatever time is remaining if less than time split
                    if (remainingTime <= timeSplit)
                    {
                        startTime += remainingTime;
                        unfinishedJobQty--;
                        queue[i].setTime(0);
                    }
                    else
                    {
                        startTime += timeSplit;
                        queue[i].setTime(remainingTime - timeSplit);
                    }
                    schedule.add(new Job(jobName, startTime));
                }
            }
        }
    }

    /**
     * Calculates the average completion time for the jobs in the queue.
     * @return Average completion time
     */
    private double calculateAverageTime()
    {
        int sum = 0;
        ArrayList<String> jobsChosen = new ArrayList<>();
        int scheduleIndex = schedule.size() - 1; //look through schedule backwards

        //loop until all jobs stop time have been accounted for (jobs been chosen in calculation)
        while (jobsChosen.size() < jobQty)
        {
            Job job = schedule.get(scheduleIndex);
            String jobName = job.getName();
            //job wasn't chosen yet - get its stop time and add to completion array and sum
            if (!jobsChosen.contains(jobName))
            {
                completionIndices[jobsChosen.size()] = scheduleIndex;
                jobsChosen.add(jobName);
                sum += job.getTime();
            }
            scheduleIndex--;
        }
        return (sum * 1.0)/jobQty;
    }

    /**
     * Retrieves the full schedule of jobs.
     * @return The job schedule
     */
    public ArrayList<Job> getSchedule() { return schedule; }

    /**
     * Retrieves the average completion time for all the jobs scheduled.
     * @return The average completion time
     */
    public double getAverageTime() { return averageTime; }

    /**
     * Retrieves the schedule indices for when a job completes.
     * @return An array of integer indices
     */
    public int[] getCompletionIndices() { return completionIndices; }

    /**
     * Retrieves the time split used for the round robin algorithm
     * @return The time split value
     */
    public int getTimeSplit() { return timeSplit; }
}
