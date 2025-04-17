import java.util.*;

public class RoundRobin extends Scheduling {

    private int quantum;

    RoundRobin (PCB[] processes, int maximumTime, int quantum) {

        super(processes, maximumTime);
        isPreemptive = true;
        readyQueue = new LinkedList<>();
        waitingQueue = new LinkedList<>();
        this.quantum = quantum;

    }

    @Override
    protected void resetProperties(PCB process, int countWaiting) {
        process.setReadyQueueTime(countWaiting); //update the time of last comeback to the current time
        process.setRemainingTime(process.burstTime); //reset the remaining time
    }

    protected void interruptProcess (int time) {

        if ((time - runningProcess.getDeploymentTime()) % quantum == 0) {

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


