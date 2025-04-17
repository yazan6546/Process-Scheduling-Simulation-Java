
/**
 * Represents a Process Control Block (PCB) for a scheduling algorithm.
 * Implements the Cloneable interface for object cloning.
 */
public class PCB implements Cloneable {

    private int pid;
    public int arrivalTime;
    public final int burstTime;
    private int remainingTime;
    private int deploymentTime;
    private int comeBack;
    private int priority;
    private int interruptTime;
    private int finishTime;
    private int executionTime;
    private int readyQueueTime;
    public int waitingQueueTime;
    public boolean hasExecuted;

    /**
     * Constructs a PCB object with the given parameters.
     *
     * @param pid         Process ID.
     * @param arrivalTime Arrival time of the process.
     * @param burstTime   Burst time of the process.
     * @param comeBack    Time for the process to come back to the ready queue.
     * @param priority    Priority of the process.
     */
    public PCB (int pid, int arrivalTime, int burstTime, int comeBack, int priority) {

        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.comeBack = comeBack;
        this.priority = priority;
        hasExecuted = false;

    }

    public int getPid () {
        return pid;
    }

    public int getRemainingTime () {
        return remainingTime;
    }

    public void setRemainingTime (int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getComeBack () {
        return comeBack;
    }

    public void decrementRemainingTime () {
        if (remainingTime > 0) {
            remainingTime--;
        }
        else {
            remainingTime = 0;
        }
    }

    /**
     * Decrements the priority of the process.
     */
    public void decrementPriority () {

        if (priority > 0)
            priority--;
        else {
            priority = 0;
        }
    }

    public void incrementExecutionTime () {
        executionTime++;
    }

    public int getPriority () {
        return priority;
    }

    public void setPriority (int priority) {
        this.priority = priority;
    }

    public int getFinishTime () {
        return finishTime;
    }

    public void setFinishTime (int finishTime) {
        this.finishTime = finishTime;
    }
    public int getExecutionTime () {
        return executionTime;
    }


    public int getReadyQueueTime () {
        return readyQueueTime;
    }

    /**
     * Sets the time when the process was in the ready queue.
     *
     * @param readyQueueTime The ready queue time.
     */
    public void setReadyQueueTime (int readyQueueTime) {
        this.readyQueueTime = readyQueueTime;
    }

    /**
     * Gets the deployment time of the process.
     *
     * @return The deployment time.
     */
    public int getDeploymentTime() {
        return deploymentTime;
    }

    /**
     * Sets the deployment time of the process.
     *
     * @param deploymentTime The deployment time.
     */
    public void setDeploymentTime(int deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    /**
     * Calculates the average waiting time for an array of processes.
     *
     * @param processes An array of PCB (Process Control Block) objects.
     * @return The average waiting time.
     */
    public static double calculateAverageWaitingTime (PCB[] processes) {

        int waiting = 0;

        for (PCB process : processes) {
            if (process.hasExecuted) {
                waiting += process.waitingQueueTime;
            }
            System.out.println(process + " Waiting time " + process.waitingQueueTime);

        }
        return (double) (waiting)/getNumberExecuted(processes);
    }


    /**
     * Calculates the average turnaround time for an array of processes.
     *
     * @param processes An array of PCB (Process Control Block) objects.
     * @return The average turnaround time.
     */
    public static double calculateAverageTurnaroundTime (PCB[] processes) {

        int sumTurnaround =  0;
        System.out.println();
        for (PCB process : processes) {
            if (process.hasExecuted) {

                sumTurnaround += process.interruptTime - process.arrivalTime;
            }

        }
        return (double) sumTurnaround/getNumberExecuted(processes);
    }

    /**
     * Gets the burst time of the process.
     *
     * @return The burst time.
     */
    public int getBurstTime() {
        return burstTime;
    }

    /**
     * Gets the number of executed processes.
     *
     * @param processes An array of PCB (Process Control Block) objects.
     * @return The number of executed processes.
     */
    private static int getNumberExecuted (PCB[] processes) {
        int count = 0;
        for (PCB process : processes) {
            if (process.hasExecuted) {
                count++;
            }
        }

        return count;
    }

    /**
     * Gets the interrupt time of the process.
     *
     * @return The interrupt time.
     */
    public int getInterruptTime() {
        return interruptTime;
    }


    /**
     * Sets the interrupt time of the process.
     *
     * @param interruptTime The interrupt time.
     */
    public void setInterruptTime(int interruptTime) {
        this.interruptTime = interruptTime;
    }


    /**
     * Overrides the clone method to support object cloning.
     *
     * @return A cloned instance of the PCB object.
     * @throws CloneNotSupportedException If cloning is not supported.
     */

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    /**
     * Overrides the toString method to provide a string representation of the PCB object.
     *
     * @return A string representation of the PCB object.
     */
    @Override
    public String toString() {
        return "P" + pid;
    }

}