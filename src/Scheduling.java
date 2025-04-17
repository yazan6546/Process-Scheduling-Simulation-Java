import java.util.*;


/**
 * Abstract class representing a generic scheduling algorithm.
 * Provides a common structure for various scheduling algorithms.
 */

public abstract class Scheduling {

    /** Array of processes to be scheduled. */
    protected PCB[] processes;

    /** Currently running process. */
    protected PCB runningProcess;

    /** Queue of processes ready to execute. */
    protected Queue<PCB> readyQueue;

    /** List of processes waiting to be scheduled. */
    protected LinkedList<PCB> waitingQueue;

    /** Maximum time for which the algorithm runs. */
    protected int maximumTime;
    /** Indicates whether the algorithm is preemptive. */
    protected boolean isPreemptive;

    /** Indicates whether the algorithm is priority-based. */
    protected boolean isPriority;
    /** Counter for tracking entries in the Gantt chart. */
    protected int count;



    /**
     * Constructs a scheduling algorithm with the given processes and maximum time.
     *
     * @param processes   An array of PCB (Process Control Block) objects.
     * @param maximumTime The maximum time for which the algorithm runs.
     */
    public Scheduling(PCB[] processes, int maximumTime) {
        setProcesses(processes);
        this.maximumTime = maximumTime;
        count = 0;

    }

    /**
     * Executes the scheduling algorithm and prints Gantt chart entries.
     *
     * This method simulates the execution of the scheduling algorithm for the specified
     * maximum time. It iterates through time units, updating the state of processes and
     * printing entries to the Gantt chart.
     *
     * @throws IllegalCallerException if the scheduling algorithm is preemptive or priority-based.
     */
    public void runAlgorithm() {

        System.out.println("Outputs for " + getClass().getName() + "\n");

        runningProcess = processes[0];
        runningProcess.setDeploymentTime(0);
        runningProcess.hasExecuted = true;

        System.out.println("\u001B[31m\u001B[1mGantt Chart\u001B[0m : ");
        System.out.println("\u001B[1m" + "_".repeat(83));

        for (int time = 1; time < maximumTime; time++) { //loop that simulates CPU scheduling

            boolean flag = true;

            if (runningProcess != null) {
                runningProcess.incrementExecutionTime();
                runningProcess.decrementRemainingTime();
            }

            if (isPriority) {
                decreasePriority(time);
            }

            checkForArrival(readyQueue, processes, time);
            checkForComeback(waitingQueue, readyQueue, time);

            if ((runningProcess != null) && (runningProcess.getRemainingTime() == 0)) {

                flag = false; //flag to not interrupt the new process that is to be chosen
                runningProcess.setFinishTime(time);
                runningProcess.setInterruptTime(time);
                waitingQueue.addLast(runningProcess);
                printGanttEntry(runningProcess, time);

                if (readyQueue.isEmpty()) {
                    runningProcess = null;
                    continue;
                }
                runningProcess = readyQueue.poll();
                runningProcess.waitingQueueTime += time - runningProcess.getReadyQueueTime();
                runningProcess.setDeploymentTime(time);
                runningProcess.hasExecuted = true;
            }
            if (flag && isPreemptive) { //no need to interrupt the process since it just started
                interruptProcess(time);
            }

        }

        if (runningProcess != null) {
            runningProcess.setFinishTime(maximumTime);
            runningProcess.setInterruptTime(maximumTime);
            printGanttEntry(runningProcess, maximumTime);
        }
        System.out.println("\n\u001B[1m" + "_".repeat(83));

        double avgWaiting = PCB.calculateAverageWaitingTime(processes);
        double avgTurnaround = PCB.calculateAverageTurnaroundTime(processes);


        System.out.println("\u001B[31m\u001B[1mAverage waiting time\u001B[0m : \u001B[1m" + avgWaiting);
        System.out.println("\u001B[31m\u001B[1mAverage turnaround time\u001B[0m : \u001B[1m" + avgTurnaround + "\n\u001B[0m");
    }


    /**
     * Sets the array of processes for the scheduling algorithm.
     *
     * @param processes An array of PCB (Process Control Block) objects.
     */
    protected void setProcesses(PCB[] processes) {
        this.processes = new PCB[processes.length];
        copyArray(this.processes, processes);
    }

    /**
     * Checks for processes returning from the waiting queue.
     *
     * @param waitingQueue  A linked list containing processes waiting to be scheduled.
     * @param readyQueue    A queue containing processes ready to execute.
     * @param countWaiting  The current count of waiting time.
     */
    public void checkForComeback(LinkedList<PCB> waitingQueue, Queue<PCB> readyQueue, int countWaiting) {
        ListIterator<PCB> iterator = waitingQueue.listIterator();

        while (iterator.hasNext()) {
            PCB process = iterator.next();

            if (hasComebackElapsed(process, countWaiting)) {
                resetProperties(process, countWaiting);
                readyQueue.offer(process);
                iterator.remove();
            }
        }
    }

    /**
     * Checks if the comeback time has elapsed for a process.
     *
     * @param process      The PCB (Process Control Block) object to be checked.
     * @param countWaiting The current count of waiting time.
     * @return true if the comeback time has elapsed, false otherwise.
     */

    protected boolean hasComebackElapsed(PCB process, int countWaiting) {
        return (process.getComeBack() + process.getFinishTime()) == countWaiting;
    }

    /**
     * Resets properties of a process after a comeback.
     *
     * @param process      The PCB (Process Control Block) object to be reset.
     * @param countWaiting The current count of waiting time.
     */
    protected abstract void resetProperties(PCB process, int countWaiting);


    protected void checkForArrival(Queue<PCB> readyQueue, PCB[] processes, int countArrival) {

        int i;
        for (i = 1; i < processes.length; i++) {

            if (processes[i].arrivalTime == countArrival) {
                processes[i].setReadyQueueTime(countArrival);
                readyQueue.offer(processes[i]);
                break; //break after finding a process because the arrival time is different for each process
            }
        }
    }

    /**
     * Throws an exception if the scheduling algorithm is non-preemptive.
     *
     * @param time The current time.
     * @throws IllegalCallerException Always throws this exception.
     */
    protected void interruptProcess (int time) {
        throw new IllegalCallerException("This algorithm is non-preemptive!");
    }

    /**
     * Throws an exception as the scheduling algorithm is non-priority.
     *
     * @param time The current time.
     * @throws IllegalCallerException Always throws this exception.
     */
    protected void decreasePriority (int time) {
        throw new IllegalCallerException("This algorithm is non-priority!");
    }

    /**
     * Copies the contents of one array to another.
     *
     * @param processes1 The destination array.
     * @param processes2 The source array.
     */
    private static void copyArray (PCB[] processes1, PCB[] processes2) {
        try {
            for (int i = 0; i < processes2.length; i++) {
                processes1[i] = (PCB) processes2[i].clone();
            }
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Prints a Gantt chart entry for a process at a specific time.
     *
     * @param process The PCB (Process Control Block) object.
     * @param time    The time at which the process is scheduled.
     */
    public void printGanttEntry (PCB process, int time) {
        count++;
        System.out.printf("\u001B[1m%-3s\u001B[31m |%-2s|\u001B[0m \u001B[1m%-3s\t", process.getDeploymentTime(), process, time);
        if (count % 5 == 0) {
            System.out.println();
        }

    }

}

