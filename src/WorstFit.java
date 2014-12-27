import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class extends the basemanager class.
 * It will hold the memory space for the worst fit implemenation.
 * @author DanielKim
 *
 */
public class WorstFit extends BaseManager {
   /**
    * The priority queue data structure.
    */
    MaxHeap<Block> pq;
    
    /**
     * Constructs a BaseManager object, and initializes ArrayLists of
     * full and free memory blocks.
     * @param memsize is the size of memory.
     */
    public WorstFit(int memsize) {
        super(memsize);
        this.pq = new MaxHeap<Block>();
        Block b = new Block(0, 0, this.maxMem);
        this.pq.insert(b);
    }
    
    /**
     * Attempt to allocate a new block of memory.
     * @param id the ID number of the block
     * @param size the block size
     * @return the Block that has been deallocated
     */
    public Block allocate(int id, int size) {
        long start = System.currentTimeMillis();
        long finish;
        Block insert = new Block(id, -1, size);
        Block free = this.pq.max();
        if (free == null || size > free.getSize()) {
            totalFails++;
            totalFailSize += size;
            return insert;
        }
        this.pq.deleteMax();
        if (free.getSize() == insert.getSize()) {
            insert.setAddress(free.getAddress());
        } else {
            insert.setAddress(free.getAddress());
            free.setAddress(free.getAddress() + size);
            free.setSize(free.getSize() - size); //adjust size
            this.pq.insert(free);
        }
        this.fullBlocks.add(insert);        
        totalAllocs++;
        finish = System.currentTimeMillis();
        totalTime += (float) (finish - start);
        return insert;        
    }
    
    /**
     * Deallocate a block of memory, and return the block.
     * @param id the ID number of the block
     * @return the Block that has been deallocated
     */
    public Block deallocate(int id) {
        Block b = super.deallocate(id);
        if (b != null) {
            this.pq.insert(b);
        }
        return b; //returns the block 
    }

    /**
     * Defragment free memory blocks.
     * @return a defragged ArrayList
     */
    public ArrayList<Block> defrag() {
        ArrayList<Block> freeBlocks;
        freeBlocks = super.defrag();
        Iterator<Block> iter = freeBlocks.iterator();
        this.pq = new MaxHeap<Block>();
        while (iter.hasNext()) {
            this.pq.insert(iter.next());
        }
        return freeBlocks;
        
    }
    
    /**
     * Return an ArrayList of the priority queue.
     * @return an ArrayList representation
     */
    public ArrayList<Block> toArray() {
        return this.pq.toArray();
    }
}