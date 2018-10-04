package JobPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This class creates a queue of jobs that are waiting to be scheduled for CPU execution. The
 * class reads a file containing all the incoming jobs and its burst times and creates a queue
 * from the information in the file. It is assumed that all jobs arrive to the queue at time 0.
 *
 * Max jobs possible per prompt is 30 jobs.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public class JobQueuer
{
    private final int MAX_JOBS = 30;
    private int queueSize;
    private Job[] queue;

    /**
     * Creates the job queue from the file listing all the jobs that have arrived to queue.
     * @param jobFile File with information about jobs and their burst times
     * @throws FileNotFoundException
     */
    public JobQueuer(File jobFile) throws FileNotFoundException
    {
        queue = new Job[MAX_JOBS];
        queueSize = 0;
        int time = -1;
        Scanner fileReader = new Scanner(jobFile);

        //reads file to create jobs
        while (fileReader.hasNext())
        {
            /* JobPackage.Job file format should be:
                One line for a job's name
                One line for the burst time of a job (from the previous line)
             */
            String name = fileReader.nextLine();
            //exception if file does not follow correct formatting for names/times
            time = Integer.parseInt(fileReader.nextLine());

            //should already be handled by parse exception; backup to ensure no fail job queuing
            if (!addJobToQueue(name, time))
                throw new InputMismatchException();
        }
        fileReader.close();
    }

    /**
     * Adds a job to the queue for awaiting CPU execution
     * @param name The name of the job
     * @param time The CPU time required for job completion
     * @return True if job queuing ia successful; false otherwise
     */
    private boolean addJobToQueue(String name, int time)
    {
        if (time > 0)
            queue[queueSize++] = new Job(name, time);
        return time > 0;
    }

    /**
     * Retreives the job queue for CPU execution.
     * @return The job queue
     */
    public Job[] getQueue() { return queue; }

    /**
     * Retrieves the number of jobs of the queue.
     * @return The number of jobs in the queue
     */
    public int getQueueSize() { return queueSize; }
}
