package JobPackage;

/**
 * <b><u>CS 431 CPU Scheduler Project</b></u>
 * <br>
 * This class is for a job for the CPU. Each job has a name and a time, where the time can be
 * either:
 * (used by JobQueuer) CPU processing time required or the stop (or burst time)
 * (used  by SchedulerInterface classes): CPU usage stop time for a job
 * It is assumed that a job arrives to the queue at time 0. All time is in ms.
 *
 * @author Lisa Chen
 * @since July 16, 2018
 */
public class Job implements Comparable<Job>
{
    private String name;
    private int time;

    /**
     * Constructs a job with a name and burst/stop time (in ms).
     * @param name The name of the job
     * @param time Burst time or stop ti9me for a job
     */
    public Job(String name, int time)
    {
        this.name = name;
        this.time = time;
    }

    /**
     * Retrieves the name of the job.
     * @return The job's name
     */
    public String getName() { return name; }

    /**
     * Retrieves the burst/stop time (in ms)
     * @return The burst/stop time
     */
    public int getTime() { return time; }

    /**
     * Sets the burst/stop time for a job.
     * @param newTime The new burst/stop time
     */
    public void setTime(int newTime) { time = newTime; }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     */
    public int compareTo(Job o) {
        if (o == null)
            throw new NullPointerException();
        return this.getTime() - o.getTime();
    }
}
