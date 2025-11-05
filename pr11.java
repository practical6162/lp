import java.util.*;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of page reference string: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the page reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        int pageFaults = 0;

        System.out.println("\nPage\tFrames");
        System.out.println("--------------------------");

        for (int page : pages) {
            // Page not in memory → page fault
            if (!set.contains(page)) {
                if (set.size() < frames) {
                    set.add(page);
                    queue.add(page);
                } else {
                    int oldest = queue.poll();
                    set.remove(oldest);
                    set.add(page);
                    queue.add(page);
                }
                pageFaults++;
            }

            // Display current frames
            System.out.print(page + "\t");
            for (int p : queue) {
                System.out.print(p + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults = " + pageFaults);
        sc.close();
    }
}
