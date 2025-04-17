
import java.util.*;

public class SRTF extends Scheduling {

    SRTF(PCB[] processes, int maximumTime) {

        super(processes, maximumTime);
        isPreemptive = true;
        readyQueue = new PriorityQueue<>(processes.length, Comparator.comparing(PCB::getRemainingTime).
                thenComparing(PCB::getReadyQueueTime));

        waitingQueue = new LinkedList<>();

    }


    @Override
    protected void resetProperties(PCB process, int countWaiting) {
        process.setReadyQueueTime(countWaiting); //update the time of last comeback to the current time
        process.setRemainingTime(process.burstTime); //reset the remaining time
    }

    protected void interruptProcess(int time) {

        if (readyQueue.peek().getRemainingTime() < runningProcess.getRemainingTime()) {

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


