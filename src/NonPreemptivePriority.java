import java.util.*;

/**
 * Implementation of Non-preemptive Priority Scheduling algorithm with aging.
 */
public class NonPreemptivePriority extends Scheduling {


    private int[] priorities;
    private int quantum;

    /**
     * Constructor for NonPreemptivePriority class.
     *
     * @param processes   Array of PCB (Process Control Block) representing the processes to be scheduled.
     * @param maximumTime Maximum time for simulation.
     * @param quantum     Time quantum for the aging mechanism.
     */
    NonPreemptivePriority (PCB[] processes, int maximumTime, int quantum) {

        super(processes, maximumTime);
        isPreemptive = false;

        /*
         * Priority queue representing the ready queue for processes.
         * Processes in this queue are ordered based on their priority, and if priorities are equal,
         * they are ordered based on their ready queue time.
         */
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

    @Override
    protected void resetProperties (PCB process, int countWaiting) {

        process.setReadyQueueTime(countWaiting);
        process.setRemainingTime(process.burstTime); //reset the remaining time
        process.setPriority(priorities[process.getPid()-1]); //reset priority
    }

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

}
