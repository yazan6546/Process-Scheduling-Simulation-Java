import java.util.*;

public class SJF extends Scheduling {


    SJF (PCB[] processes, int maximumTime) {

        super(processes, maximumTime);
        isPreemptive = false;
        readyQueue = new PriorityQueue<>(processes.length, Comparator.comparing(PCB::getBurstTime).
                thenComparing(PCB::getReadyQueueTime));
        waitingQueue = new LinkedList<>();

    }


    @Override
    protected void resetProperties (PCB process, int countWaiting) {
        process.setReadyQueueTime(countWaiting); //update the time of last comeback to the current time
        process.setRemainingTime(process.burstTime); //reset the remaining time
    }
}


