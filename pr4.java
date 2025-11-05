import java.util.*;

class Process {
    int pid;
    int arrival;
    int burst;
    int remaining;
    int completion;
    int waiting;
    int turnaround;
}

public class SJF_Preemptive {
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
            p[i].remaining = p[i].burst;
        }

        int completed = 0, currentTime = 0, prev = -1;
        List<Integer> gantt = new ArrayList<>();

        while (completed < n) {
            int idx = -1;
            int min = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (p[i].arrival <= currentTime && p[i].remaining > 0 && p[i].remaining < min) {
                    min = p[i].remaining;
                    idx = i;
                }
            }

            if (idx != -1) {
                if (prev != idx) gantt.add(p[idx].pid);
                p[idx].remaining--;
                currentTime++;

                if (p[idx].remaining == 0) {
                    p[idx].completion = currentTime;
                    p[idx].turnaround = p[idx].completion - p[idx].arrival;
                    p[idx].waiting = p[idx].turnaround - p[idx].burst;
                    completed++;
                }
                prev = idx;
            } else {
                currentTime++;
            }
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("PID\tArrival\tBurst\tWaiting\tTurnaround\tCompletion");
        System.out.println("------------------------------------------------------------");
        double totalWT = 0, totalTAT = 0;
        for (Process x : p) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t\t%d\n",
                    x.pid, x.arrival, x.burst, x.waiting, x.turnaround, x.completion);
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
    }
}
