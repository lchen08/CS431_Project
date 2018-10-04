package SchedulerPackage;

import JobPackage.Job;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is a scheduler using the shortest-job-first algorithm to create a schedule of jobs
 * for the order and how the jobs are processed by the CPU. The schedule is created by an
 * input job queue with information about the jobs in the queue.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public class SJFScheduler implements SchedulerInterface
{
    ArrayList<Job> schedule;
    double averageTime;

    /**
     * Constructs the shortest-job-first scheduler using a job queue and its size.
     * @param queue The queue of jobs awaiting CPU processing
     * @param queueSize The number of jobs in the queue
     */
    public SJFScheduler(Job[] queue, int queueSize)
    {
        schedule = new ArrayList<>();
        createSchedule(queue, queueSize);
        averageTime = calculateAverageTime();
    }

    /**
     * Craetes the CPU job schedule using shortest-job-first algorithm.
     * @param queue The queue of jobs awaiting CPU processing
     * @param queueSize THe number of jobs in the queue
     */
    private void createSchedule (Job[] queue, int queueSize)
    {
        int startTime = 0;
        Job[] sortedQueue = Arrays.copyOf(queue,queueSize); //prevent altering given queue

        //sort jobs by smallest to largest completion time
        Arrays.sort(sortedQueue);

        //loop to add jobs to schedule
        for (int i = 0; i < queueSize; i++)
        {
            String jobName = sortedQueue[i].getName();
            startTime += sortedQueue[i].getTime();
            schedule.add(new Job(jobName, startTime));
        }
    }

    /**
     * Calculates the average completion time for the jobs in the queue.
     * @return Average completion time
     */
    protected double calculateAverageTime()
    {
        int sum = 0;
        int count = 0;
        for (Job job : schedule)
        {
            sum += job.getTime();
            count++;
        }
        return (sum * 1.0)/count;
    }

    /**
     * Retrieves the full schedule of jobs.
     * @return The job schedule
     */
    public ArrayList<Job> getSchedule() { return schedule; }

    /**
     * Reterives the avearge completion time for all the jobs scheduled.
     * @return The average completion time
     */
    public double getAverageTime() { return averageTime; }
}
