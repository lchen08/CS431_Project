package SchedulerPackage;

import JobPackage.Job;
import java.util.ArrayList;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This is an interface for CPU schedulers where the job schedule created by the scheduler and
 * the average job completion time can be retrieved.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public interface SchedulerInterface
{
    /**
    * Retrieves the full schedule of jobs.
    * @return The job schedule
    */
    ArrayList<Job> getSchedule();

    /**
    * Reterives the avearge completion time for all the jobs scheduled.
    * @return The average completion time
    */
    double getAverageTime();
}