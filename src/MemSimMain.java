/**
 * Memory Simulation for 600.226 - partial main driver for I/O handling.
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Driver for MemSimulator project - partial skeleton for I/O handling.
 */

final class MemSimMain {

    /**
     * Empty Constructor for Checkstyle.
     */
    private MemSimMain() {

    }

    /**
     * Main program.
     * @param args for Checkstyle
     * @throws FileNotFoundException if file not found
     * @throws IOException for bad filename
     */
    public static void main(String[] args) throws FileNotFoundException,
            IOException {

        /**
         * The number of implementations of MemoryManager.
         */
        final int numOfImplementations = 3;

        PrintWriter translog = new PrintWriter("translog.txt");

        // read input filename from keyboard, or get from command-line args
        String filename = "";
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter filename: ");
            filename = sc.nextLine();
            sc.close();
        } else {
            filename = args[0];
        }

        Scanner input = new Scanner(new File(filename));
        int memSize = 0;

        try {
            memSize = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            memSize = -1;
        }

        MemoryManager[] sims = new MemoryManager[numOfImplementations];
        sims[0] = new BestFit(memSize);
        sims[1] = new WorstFit(memSize);
        sims[2] = new CloseFit(memSize);

        int idCounter = 1;
        int id;
        ArrayList<String> lines = new ArrayList<String>();
        while (input.hasNext()) {
            // System.out.println(++counter);
            String output = "";
            try {
                switch (input.next()) {
                    case "A":
                        id = idCounter++;
                        int size = input.nextInt();
                        
                        System.out.println(id + " A " + size);

                        for (int i = 0; i < numOfImplementations; i++) {
                            Block b = sims[i].allocate(id, size);
                            if (b.getAddress() == -1) {
                                sims[i].defrag();
                                b = sims[i].allocate(id, size);
                                output = formatAlloc(output, b.getAddress(),
                                        true, id, size);
                            } else {
                                output = formatAlloc(output, b.getAddress(),
                                        false, id, size);
                            }
                        }

                        break;

                    case "D":
                        id = input.nextInt();
                        
                        System.out.println("D " + id);
                        for (int a = 0; a < numOfImplementations; a++) {
                            Block b = sims[a].deallocate(id);
                            output = formatDealloc(output, b, id);
                        }
                        break;

                    default:
                        System.out.println("Error, could not read.");

                }
                lines.add(output);

            } catch (InputMismatchException e) {
                System.err.println(e);
                input.nextLine();
            }
        }

        // insert lots of stuff here to fill lines, calling methods below

        printTrans(translog, lines, memSize, filename);
        translog.close();
        PrintWriter analysis = new PrintWriter("analysis.txt");
        printOutput(analysis, sims, memSize, filename);

        analysis.close();
        input.close();

    }



    /**
     * Formats allocation attempt for transaction log.
     * @param output String with allocation request info.
     * @param address Current address of allocation
     * @param defragged True if defrag occurred, false otherwise
     * @param id Current id number
     * @param size Allocation size
     * @return String representation of the allocation
     */
    public static String formatAlloc(String output, int address,
            boolean defragged, int id, int size) {

        String defrag = "";
        String sucString = "SUCCESS";

        if (defragged) {
            defrag = "DF";
        }
        if (address == -1) {
            sucString = "FAILED";
        }
        output += String.format("A%4d%4s  %-7s%6s%7s        ", id, defrag,
                sucString, address, size);

        return output;
    }

    /**
     * Formats lines from deallocations for transaction log.
     * @param output String with deallocation info.
     * @param temp Block from deallocation
     * @param id Current id number
     * @return String representation of the deallocation
     */
    public static String formatDealloc(String output, Block temp, int id) {

        String sucString = "SUCCESS";
        int deallocSize = 0;
        int address = 0;

        if (temp == null) {
            sucString = "FAILED";
            address = -1;
        } else {
            address = temp.getAddress();
            deallocSize = temp.getSize();
        }
        output += String.format("D%4s%4s  %-7s%6s%7s        ", id, "",
                sucString, address, deallocSize);
        return output;
    }

    /**
     * Print lines to transaction log.
     * @param outPut PrintWriter for transaction log
     * @param lines Arraylist of lines to be printed
     * @param memSize the size of the memory
     * @param filename the name of the input file
     */
    public static void printTrans(PrintWriter outPut, ArrayList<String> lines,
            int memSize, String filename) {

        outPut.println("Memory Transaction Log");
        outPut.println("Memory Size: " + memSize);
        outPut.println("Input file: " + filename);
        outPut.println("Memory Address begins at location 0");
        outPut.println();

        outPut.println("-------------------------------------"
                + "-----------------"
                + "----------------------------------------------------------");
        outPut.println("           Best Fit                                "
                + "Worst Fit                               Close Fit");
        outPut.println("--------------------------------------"
                + "----------------"
                + "----------------------------------------------------------");
        outPut.println("   ID  DF? Success  Addr.  Size           ID  DF? "
                + "Success  Addr.  Size           ID  DF?"
                + " Success  Addr.  Size");
        outPut.println();

        for (String item : lines) {
            outPut.println(item);
        }
    }

    /**
     * Print lines to analysis log.
     * @param outPut PrintWriter for analysis log
     * @param sims Array of MemoryManagers
     * @param memSize Amount of memory in this simulation
     * @param filename Input file name
     */
    public static void printOutput(PrintWriter outPut, MemoryManager[] sims,
            int memSize, String filename) {

        outPut.println("Performance Analysis Chart");
        outPut.println("Memory Size " + memSize);
        outPut.println("Input File Used: " + filename);
        outPut.println("---------------------------------------------------"
                + "-------------------------");
        outPut.println("Statistics:                        Best Fit    "
                + "Worst Fit    Close Fit");
        outPut.println("---------------------------------------------------"
                + "-------------------------");
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "Number of Defragmentations:",
                sims[0].defragCount(), sims[1].defragCount(),
                sims[2].defragCount());
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "# of failed allocation requests:",
                sims[0].totalFails(), sims[1].totalFails(),
                sims[2].totalFails());
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "Average size failed allocs:",
                sims[0].avgFailSize(), sims[1].avgFailSize(),
                sims[2].avgFailSize());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time to process alloc*:", sims[0].avgTime(),
                sims[1].avgTime(), sims[2].avgTime());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size quicksort*:", sims[0].avgQuicksortRatio(),
                sims[1].avgQuicksortRatio(), sims[2].avgQuicksortRatio());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size bucketsort*:", sims[0].avgBucketsortRatio(),
                sims[1].avgBucketsortRatio(), sims[2].avgBucketsortRatio());
        outPut.println("");
        outPut.println("");
        outPut.println("*All times in microseconds.");
        outPut.close();
    }
}
