import java.util.*;

public class WorstFit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int nb = sc.nextInt();
        int[] blockSize = new int[nb];
        int[] blockAllocated = new int[nb];

        System.out.println("Enter size of each memory block:");
        for (int i = 0; i < nb; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSize[i] = sc.nextInt();
            blockAllocated[i] = -1; // not allocated yet
        }

        System.out.print("\nEnter number of processes: ");
        int np = sc.nextInt();
        int[] processSize = new int[np];
        int[] allocation = new int[np];
        Arrays.fill(allocation, -1);

        System.out.println("Enter size of each process:");
        for (int i = 0; i < np; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSize[i] = sc.nextInt();
        }

        // Worst Fit Allocation
        for (int i = 0; i < np; i++) {
            int worstIdx = -1;
            for (int j = 0; j < nb; j++) {
                if (blockAllocated[j] == -1 && blockSize[j] >= processSize[i]) {
                    if (worstIdx == -1 || blockSize[j] > blockSize[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }
            if (worstIdx != -1) {
                allocation[i] = worstIdx;
                blockAllocated[worstIdx] = i;
            }
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Process No.\tProcess Size\tBlock Allocated");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < np; i++) {
            if (allocation[i] != -1)
                System.out.println((i + 1) + "\t\t" + processSize[i] + "\t\tBlock " + (allocation[i] + 1));
            else
                System.out.println((i + 1) + "\t\t" + processSize[i] + "\t\tNot Allocated");
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Block No.\tBlock Size\tAllocated\tUnused Space");
        System.out.println("------------------------------------------------------------");
        for (int j = 0; j < nb; j++) {
            if (blockAllocated[j] != -1) {
                int unused = blockSize[j] - processSize[blockAllocated[j]];
                System.out.println((j + 1) + "\t\t" + blockSize[j] + "\t\tP" + (blockAllocated[j] + 1) + "\t\t" + unused);
            } else {
                System.out.println((j + 1) + "\t\t" + blockSize[j] + "\t\tFree\t\t" + blockSize[j]);
            }
        }

        sc.close();
    }
}
