import java.util.Scanner;

/**
 * The main driver class to execute the scheduling algorithms.
 */
public class Driver {

    public static void main(String[] args) {

        PCB[] processes = {new PCB(1, 0, 10, 2, 3),
                new PCB(2, 1, 8, 4, 2),
                new PCB(3, 3, 14, 6, 3),
                new PCB(4, 4, 7, 8, 1),
                new PCB(5, 6, 5, 3, 0),
                new PCB(6, 7, 4, 6, 1),
                new PCB(7, 8, 6, 9, 2)};


       while (true) {

           Scanner scanner = new Scanner(System.in);

           showMenu();
           int input = scanner.nextInt();

           Scheduling scheduler = switch (input) {
               case 1 -> new FCFS(processes, 200);
               case 2 -> new SJF(processes, 200);
               case 3 -> new SRTF(processes, 200);
               case 4 -> new RoundRobin(processes, 200, 5);
               case 5 -> new PreemptivePriority(processes, 200, 5);
               case 6 -> new NonPreemptivePriority(processes, 200, 5);
               default -> {
                   System.out.println("Terminating program...");
                   System.exit(0);
                   yield null;
                   //do nothing here
               }
           };

           scheduler.runAlgorithm();
       }
    }

    public static void showMenu () {
        System.out.println("-----------Scheduling Algorithms------------\n\n" +
                            "1) First Come First Serve\n" +
                            "2) Shortest Job First\n" +
                            "3) Shortest Remaining Time First\n" +
                            "4) Round Robin\n" +
                            "5) Preemptive priority scheduling with aging\n" +
                            "6) Non-preemptive priority scheduling with aging\n");
        System.out.println("Enter a number to choose an algorithm, other choice to exit.\n");
    }
}
