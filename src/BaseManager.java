import java.util.ArrayList;



/**
 * The BaseManager class, to be implemented using different data structures.
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 */
public class BaseManager implements MemoryManager {
    
    /**
     * ArrayList of full memory blocks.
     */
    ArrayList<Block> fullBlocks;

    /**
     * Total number of defrags performed.
     */
    int totalDefrags;
    /**
     * Total number of failed allocations.
     */
    int totalFails; 
    /**
     * Total size of all failed allocations.
     */
    int totalFailSize;
    /**
     * Total number of allocations.
     */
    int totalAllocs;
    /**
     * Total items that have been sorted.
     */
    int sortedItems;
    /**
     * Total time taken during allocations.
     */
    float totalTime;
    /**
     * Total time taken during quicksort.
     */
    float quicksortTime; 
    /**
     * Total time taken during bucketsort.
     */
    int bucketsortTime;
    
    /**
     * 
     */
    int maxMem;
    
    
    
    /**
     * Constructs a BaseManager object, and initializes ArrayLists of
     * full and free memory blocks.
     * @param capacity the memory capacity
     */
    public BaseManager(int capacity) {
        this.maxMem = capacity;
        this.fullBlocks = new ArrayList<Block>();
        this.totalDefrags = 0;
        this.totalFails = 0;
        this.totalFailSize = 0;
        this.totalAllocs = 0;
        this.sortedItems = 0;
        this.totalTime = 0;
        this.quicksortTime = 0;
        this.bucketsortTime = 0;
    }
    

    /**
     * Attempt to allocate a new block of memory.
     * @param id the ID number of the block
     * @param size the block size
     * @return the Block that has been deallocated
     */
    public Block allocate(int id, int size) {
        //dummy method to be overridden
        return null;
    }
    
    /**
     * Deallocate a block of memory, and return the block.
     * @param id the ID number of the block
     * @return the Block that has been deallocated
     */
    public Block deallocate(int id) {
        
        for (Block b : this.fullBlocks) {
            if (b.getID() == id) {
                this.fullBlocks.remove(b);
                b.setID(0);
                return b; //returns the block 
            }
        }
        return null;
    }

    /**
     * Defragment free memory blocks.
     * @return a defragged ArrayList
     */
    public ArrayList<Block> defrag() {
        
        ArrayList<Block> freeBlocks = this.toArray();
        ArrayList<Block> copy = new ArrayList<Block>(freeBlocks.size());
        copy.addAll(freeBlocks);
        
        long start = System.currentTimeMillis();
        Sorter.quickSort(freeBlocks, 0, freeBlocks.size() - 1);
        long finish = System.currentTimeMillis();
        this.quicksortTime += (float) (finish - start);
        
        start = System.currentTimeMillis();
        Sorter.bucketSort(copy, this.maxMem);
        finish = System.currentTimeMillis();
        this.bucketsortTime += (float) (finish - start);
        
        this.sortedItems += freeBlocks.size(); //add to total sorted
        
        int i = 0;
        
        while (i < freeBlocks.size() - 1) {
            Block b = freeBlocks.get(i);
            Block next = freeBlocks.get(i + 1);
            int end = b.getAddress() + b.getSize();
            if ((end) == next.getAddress()) {
                b.setSize(b.getSize() + next.getSize());
                freeBlocks.remove(next);
                //don't iterate, may need to absorb more
            } else {
                i++;
            }
        }
        
        this.totalDefrags++;
        return freeBlocks;
        
    }
    
    /**
     * @return an ArrayList representation
     */
    public ArrayList<Block> toArray() {
        return null;
    }
    
    /**
     * Return the total number of defrags.
     * @return the number of defrags
     */
    public int defragCount() {

        return this.totalDefrags;
    }

    /**
     * Return the total number of failed allocation attempts.
     * @return the number of fails
     */
    public int totalFails() {

        return this.totalFails;
    }

    /**
     * Return the average size of failed allocations.
     * @return the average fail size
     */
    public float avgFailSize() {

        return (this.totalFailSize / this.totalFails);
    }

    /**
     * Return the average time to process allocations.
     * @return the average time to process alloc
     */
    public float avgTime() {

        return (this.totalTime / this.totalAllocs);
    }

    /**
     * Return the average time/size ratio for performing quicksort.
     * @return the average time/size ratio for quicksort
     */
    public float avgQuicksortRatio() {

        return (this.quicksortTime / this.sortedItems);
    }

    /**
     * Return the average time/size ration for performing bucketsort.
     * @return the average time/size ratio for bucketsort
     */
    public float avgBucketsortRatio() {

        return (this.bucketsortTime / this.sortedItems);
    }


}
