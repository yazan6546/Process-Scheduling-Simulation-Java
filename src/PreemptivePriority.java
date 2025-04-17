import java.util.*;

/**
 * Represents a preemptive priority scheduling algorithm.
 * Extends the generic Scheduling class.
 */
public class PreemptivePriority extends Scheduling {

    /** Array to store the priorities of processes. */
    private int priorities[];

    /** Quantum time for decreasing priority. */
    private int quantum;

    /**
     * Constructs a PreemptivePriority object with the given processes, maximum time, and quantum.
     *
     * @param processes   An array of PCB (Process Control Block) objects.
     * @param maximumTime The maximum time for which the algorithm runs.
     * @param quantum     The quantum time for decreasing priority.
     */
    PreemptivePriority (PCB[] processes, int maximumTime, int quantum) {

        super(processes, maximumTime);
        isPreemptive = true;
        readyQueue = new PriorityQueue<>(processes.length,
                Comparator.comparing(PCB::getPriority).thenComparing(PCB::getReadyQueueTime));

        waitingQueue = new LinkedList<>();

        this.quantum = quantum;
        priorities = new int[processes.length];
        for (int i = 0;i<priorities.length;i++) {
            priorities[i] = processes[i].getPriority();
        }
        isPriority = true;
    }


    /**
     * Resets properties of a process after a comeback.
     *
     * @param process      The PCB (Process Control Block) object to be reset.
     * @param countWaiting The current count of waiting time.
     */
    @Override
    protected void resetProperties (PCB process, int countWaiting) {

        process.setReadyQueueTime(countWaiting);
        process.setRemainingTime(process.burstTime); //reset the remaining time
        process.setPriority(priorities[process.getPid()-1]); //reset priority
    }


    /**
     * Decreases the priority of processes in the ready queue after a specific quantum time.
     *
     * @param time The current time.
     */
    protected void decreasePriority (int time) {

        Iterator<PCB> iterator = readyQueue.iterator();
        LinkedList<PCB> stack = new LinkedList<>();
        while (iterator.hasNext()) {

            PCB process = iterator.next();
            if ((time - process.getReadyQueueTime()) % quantum == 0) { //decrement priority if a process remains in the ready queue for 5 sec

                process.decrementPriority();
                iterator.remove();
                stack.push(process);
            }
        }
        while (!stack.isEmpty()) {
            readyQueue.offer(stack.pop());
        }
    }

    /**
     * Handles the interruption of the running process based on priority.
     *
     * @param time The current time.
     */
    @Override
    protected void interruptProcess (int time) {

        if (readyQueue.peek().getPriority() < runningProcess.getPriority()) {

            printGanttEntry(runningProcess, time);
            runningProcess.setInterruptTime(time);

            PCB tempProcess = runningProcess;
            tempProcess.setReadyQueueTime(time);
            runningProcess = readyQueue.poll();
            runningProcess.waitingQueueTime += time - runningProcess.getReadyQueueTime();
            runningProcess.setDeploymentTime(time);
            runningProcess.hasExecuted = true;
            readyQueue.offer(tempProcess);
        }
    }
}


