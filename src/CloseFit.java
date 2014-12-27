import java.util.ArrayList;
import java.util.Iterator;

/** Close fit class that implements a hash table.
 * implementation for the allocation
 * @author Eddie Kong, Matt Gigliotti, Daniel Kim
 */
public class CloseFit extends BaseManager {

    /** Load factor for hash table. */
    private static final double LOAD_FACTOR = 0.75;
    
    /** Hash table of blocks for storing empty blocks. */
    private HashTable<Block> hashy;
    
    /** Maximum capacity for the memory. */
    private final int maxMem;
    
    /** Constructor for CloseFit.
     * Implements the hash table from HashTable.java
     * @param memCapacity - maximum memory capacity
     */
    public CloseFit(int memCapacity) {
        super(memCapacity); 
        this.maxMem = memCapacity;
        this.hashy = new HashTable<Block>(this.maxMem);
        // added this line- initializing first block of max capacity
        Block b = new Block(0, 0, this.maxMem);
        this.hashy.insert(b);
    }
    
    /** Method to allocate a block into the fullBlocks array.
     * call the remove method on the hash table and see if we get
     * a block. if so, we also remove it from the free blocks array, 
     * add a new block to the used blocks array and then create
     * a new block containing the new address and size to both
     * the full blocks array and the hash table.
     * @param id the id of the block to be added to fullblocks
     * @param size the size of the block to be added
     * @return the block after success. null if failed
     */
    public Block allocate(int id, int size) {
        long start = System.currentTimeMillis();
        long finish;
        Block holder = new Block(id, -1, size);
        Block free = this.hashy.remove(size);
        if (free == null) {
            totalFails++;
            totalFailSize += holder.getSize();
            return holder;
        }
        holder.setAddress(free.getAddress());
        //Block newBlock = new Block(id, holder.getAddress(), size);
        fullBlocks.add(holder);
        
        //leftover block id = 0, address = old address + size, size =
        //old size - newblock size
        free.setAddress(free.getAddress() + holder.getSize()); 
        free.setSize(free.getSize() - holder.getSize());
        if (free.getSize() > 0) {
            this.hashy.insert(free);
        }
        //check if hash table needs rehashing
        if ((this.hashy.getElements() / this.hashy.getBuckets())
                >= LOAD_FACTOR) {
            this.hashy = this.hashy.rehash(this.hashy.getElements());
        }
        
        totalAllocs++;
        finish = System.currentTimeMillis();
        totalTime += (float) (finish - start);
        return holder;
    }
    
    /** Method to deallocate memory.
     * Inherits deallocate method from its base class
     * and passes that block to the hash table to deallocate
     * @param id the id of the object to deallocate
     * @return the block that was deallocated
     */
    public Block deallocate(int id) {
        Block b = super.deallocate(id);
        if (b != null) {
            this.hashy.insert(b);
        }
        //check if hash table needs rehashing
        if ((this.hashy.getElements() / this.hashy.getBuckets())
                >= LOAD_FACTOR) {
            this.hashy = this.hashy.rehash(this.hashy.getElements());
        }
        return b;
    }
    
    /** Returns an unordered array list of empty blocks from data structure. 
     * @return the unordered array list
     * */
    public ArrayList<Block> toArray() {
        //initialize an array
        ArrayList<Block> freeArray = new ArrayList<Block>(
                this.hashy.getElements());
        //call to array method in hash table class
        freeArray = this.hashy.toArray();
        return freeArray;
    }
    /**
     * Defragment free memory blocks.
     * @return a defragged ArrayList
     */
    public ArrayList<Block> defrag() {
        ArrayList<Block> freeBlocks;
        freeBlocks = super.defrag();
        Iterator<Block> iter = freeBlocks.iterator();
        this.hashy = new HashTable<Block>(this.maxMem);
        while (iter.hasNext()) {
            this.hashy.insert(iter.next());
        }
        return freeBlocks;
        
    }
}
