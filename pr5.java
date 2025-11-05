import java.util.*;

class Process {
    int pid;
    int arrival;
    int burst;
    int priority;
    int waiting;
    int turnaround;
    int completion;
}

public class Priority_NonPreemptive {
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
            System.out.print("Priority (smaller number = higher priority): ");
            p[i].priority = sc.nextInt();
        }

        int completed = 0, currentTime = 0;
        boolean[] done = new boolean[n];
        List<Integer> gantt = new ArrayList<>();

        while (completed < n) {
            int idx = -1;
            int bestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!done[i] && p[i].arrival <= currentTime) {
                    if (p[i].priority < bestPriority) {
                        bestPriority = p[i].priority;
                        idx = i;
                    } else if (p[i].priority == bestPriority && p[i].arrival < p[idx].arrival) {
                        idx = i;
                    }
                }
            }

            if (idx != -1) {
                gantt.add(p[idx].pid);
                currentTime += p[idx].burst;
                p[idx].completion = currentTime;
                p[idx].turnaround = p[idx].completion - p[idx].arrival;
                p[idx].waiting = p[idx].turnaround - p[idx].burst;
                done[idx] = true;
                completed++;
            } else {
                currentTime++;
            }
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("PID\tAT\tBT\tPR\tWT\tTAT\tCT");
        System.out.println("------------------------------------------------------------");
        double totalWT = 0, totalTAT = 0;
        for (Process x : p) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
                    x.pid, x.arrival, x.burst, x.priority, x.waiting, x.turnaround, x.completion);
            totalWT += x.waiting;
            totalTAT += x.turnaround;
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / n);
        System.out.printf("Average Turnaround Time: %.2f\n", totalTAT / n);

        System.out.println("\nGantt Chart (Order of Execution):");
        for (int pid : gantt)
            System.out.print("|  P" + pid + "  ");
        System.out.println("|");

        sc.close();
    }
}
