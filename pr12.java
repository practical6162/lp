import java.util.*;

public class LRUPageReplacement {
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

        ArrayList<Integer> frameList = new ArrayList<>(frames);
        int pageFaults = 0;

        System.out.println("\nPage\tFrames");
        System.out.println("--------------------------");

        for (int page : pages) {
            // If page not present in memory
            if (!frameList.contains(page)) {
                if (frameList.size() < frames) {
                    frameList.add(page);
                } else {
                    // Remove least recently used (first element)
                    frameList.remove(0);
                    frameList.add(page);
                }
                pageFaults++;
            } else {
                // If page is already in memory, move it to most recently used position
                frameList.remove((Integer) page);
                frameList.add(page);
            }

            // Display frame contents
            System.out.print(page + "\t");
            for (int p : frameList) {
                System.out.print(p + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Page Faults = " + pageFaults);
        sc.close();
    }
}
