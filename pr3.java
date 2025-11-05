
import java.util.*;

class Process {
    int pid;
    int arrival;
    int burst;
    int waiting;
    int turnaround;
    int completion;
}

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.println("\nEnter details for Process " + (i + 1) + ":");
            System.out.print("Process ID: ");
            p[i].pid = sc.nextInt();
            System.out.print("Arrival Time: ");
            p[i].arrival = sc.nextInt();
            System.out.print("Burst Time: ");
            p[i].burst = sc.nextInt();
        }

        Arrays.sort(p, Comparator.comparingInt(a -> a.arrival));

        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrival)
                currentTime = p[i].arrival;

            p[i].waiting = currentTime - p[i].arrival;
            p[i].completion = currentTime + p[i].burst;
            p[i].turnaround = p[i].completion - p[i].arrival;
            currentTime = p[i].completion;
        }

        System.out.println("\n----------------------------------------------------------");
        System.out.println("PID\tArrival\tBurst\tWaiting\tTurnaround\tCompletion");
        System.out.println("----------------------------------------------------------");
        double totalWait = 0, totalTurn = 0;
        for (Process x : p) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t\t%d\n",
                    x.pid, x.arrival, x.burst, x.waiting, x.turnaround, x.completion);
            totalWait += x.waiting;
            totalTurn += x.turnaround;
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("Average Waiting Time: %.2f\n", totalWait / n);
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurn / n);

        System.out.println("\nGantt Chart:");
        for (Process x : p)
            System.out.print("|  P" + x.pid + "  ");
        System.out.println("|");

        int time = 0;
        System.out.print(p[0].arrival);
        for (Process x : p)
            System.out.print("   " + x.completion);
        System.out.println();

        sc.close();
    }
}

