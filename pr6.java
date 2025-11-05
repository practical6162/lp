import java.util.*;

class Process {
    int pid;
    int arrival;
    int burst;
    int remaining;
    int waiting;
    int turnaround;
    int completion;
}

public class RoundRobin {
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

        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();

        Queue<Integer> q = new LinkedList<>();
        List<Integer> gantt = new ArrayList<>();

        int currentTime = 0;
        boolean[] inQueue = new boolean[n];
        int completed = 0;

        while (completed < n) {
            for (int i = 0; i < n; i++) {
                if (p[i].arrival <= currentTime && !inQueue[i] && p[i].remaining > 0) {
                    q.add(i);
                    inQueue[i] = true;
                }
            }

            if (q.isEmpty()) {
                currentTime++;
                continue;
            }

            int idx = q.poll();
            gantt.add(p[idx].pid);

            int execTime = Math.min(tq, p[idx].remaining);
            p[idx].remaining -= execTime;
            currentTime += execTime;

            for (int i = 0; i < n; i++) {
                if (p[i].arrival <= currentTime && !inQueue[i] && p[i].remaining > 0) {
                    q.add(i);
                    inQueue[i] = true;
                }
            }

            if (p[idx].remaining > 0) {
                q.add(idx);
            } else {
                p[idx].completion = currentTime;
                p[idx].turnaround = p[idx].completion - p[idx].arrival;
                p[idx].waiting = p[idx].turnaround - p[idx].burst;
                completed++;
            }
            inQueue[idx] = false;
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("PID\tAT\tBT\tWT\tTAT\tCT");
        System.out.println("------------------------------------------------------------");
        double totalWT = 0, totalTAT = 0;
        for (Process x : p) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n",
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

        sc.close();
    }
}
