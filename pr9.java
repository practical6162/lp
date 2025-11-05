import java.util.*;

public class NextFit {
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
            blockAllocated[i] = -1; // not allocated
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

        int lastAllocated = 0;

        // Next Fit Allocation
        for (int i = 0; i < np; i++) {
            int count = 0;
            boolean allocated = false;

            while (count < nb) {
                int j = (lastAllocated + count) % nb;
                if (blockAllocated[j] == -1 && blockSize[j] >= processSize[i]) {
                    allocation[i] = j;
                    blockAllocated[j] = i;
                    lastAllocated = j; // start next search from here
                    allocated = true;
                    break;
                }
                count++;
            }

            if (!allocated) {
                allocation[i] = -1;
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
        System.out.println("Block No.\tBlock Size\tAllocated\tFragment");
        System.out.println("------------------------------------------------------------");
        for (int j = 0; j < nb; j++) {
            if (blockAllocated[j] != -1) {
                int frag = blockSize[j] - processSize[blockAllocated[j]];
                System.out.println((j + 1) + "\t\t" + blockSize[j] + "\t\tP" + (blockAllocated[j] + 1) + "\t\t" + frag);
            } else {
                System.out.println((j + 1) + "\t\t" + blockSize[j] + "\t\tFree\t\t" + blockSize[j]);
            }
        }

        sc.close();
    }
}
