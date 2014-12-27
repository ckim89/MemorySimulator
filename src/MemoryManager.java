import java.util.ArrayList;



/**
 * Memory Management interface for assignment 4. 
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 */
public interface MemoryManager {
    
    /**
     * Attempt to allocate a new block of memory.
     * @param id the ID number of the block
     * @param size the block size
     * @return the Block if insert was successful, null otherwise
     */
    Block allocate(int id, int size);
    
    /**
     * Deallocate a block of memory, and return the block.
     * @param id the ID number of the block
     * @return the Block that has been deallocated
     */
    Block deallocate(int id);
    
    /**
     * Defragment free memory blocks.
     * @return a defragged ArrayList
     */
    ArrayList<Block> defrag();
    
    /**
     * @return the number of defragmentations
     */
    int defragCount();
    
    /**
     * @return the number of fails
     */
    int totalFails();
    
    /**
     * @return the average fail size
     */
    float avgFailSize();
    
    /**
     * @return the average time to process alloc
     */
    float avgTime();
    
    /**
     * @return the average time/size ratio for quicksort
     */
    float avgQuicksortRatio();
    
    /**
     * @return the average time/size ratio for bucket sort
     */
    float avgBucketsortRatio();
    

}
