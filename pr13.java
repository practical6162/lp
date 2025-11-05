import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int framesCount = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("\nPage Replacement Process:");
        for (int i = 0; i < n; i++) {
            int page = pages[i];

            if (frames.contains(page)) {
                System.out.println("Page " + page + " -> No Replacement");
            } else {
                if (frames.size() < framesCount) {
                    frames.add(page);
                } else {
                    int farthest = -1, replaceIndex = -1;
                    for (int j = 0; j < frames.size(); j++) {
                        int currentPage = frames.get(j);
                        int nextUse = Integer.MAX_VALUE;
                        for (int k = i + 1; k < n; k++) {
                            if (pages[k] == currentPage) {
                                nextUse = k;
                                break;
                            }
                        }
                        if (nextUse > farthest) {
                            farthest = nextUse;
                            replaceIndex = j;
                        }
                    }
                    frames.set(replaceIndex, page);
                }
                pageFaults++;
                System.out.println("Page " + page + " -> " + frames);
            }
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
        sc.close();
    }
}
