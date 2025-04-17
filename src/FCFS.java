import java.util.*;

public class FCFS extends Scheduling {

    FCFS (PCB[] processes, int maximumTime) {

        super(processes, maximumTime);
        isPreemptive = false;
        readyQueue = new LinkedList<>();
        waitingQueue = new LinkedList<>();
    }

    @Override
    protected void resetProperties (PCB process, int countWaiting) {
        process.setReadyQueueTime(countWaiting); //update the time of last comeback to the current time
        process.setRemainingTime(process.burstTime); //reset the remaining time
    }

}


