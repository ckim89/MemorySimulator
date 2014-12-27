import java.util.ArrayList;
import java.util.Iterator;



/**
 * BestFit class, and implementation of the MemoryManager interface
 * and a subclass of BaseManager. Allocates memory by performing a 
 * best fit search on available memory, using an AVL tree. 
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 */
public class BestFit extends BaseManager {
    
    /**
     * The root node of the tree.
     */
    public AVLNode<Block> root;
    
    /**
     * Construct a BestFit object, and create a root node.
     * @param capacity the memory capacity
     */
    public BestFit(int capacity) {
        super(capacity);
        Block b = new Block(0, 0, this.maxMem);
        this.root = new AVLNode<Block>(b);
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
        Block b = new Block(id, -1, size);
        Block free = this.root.removeBestFit(b, this.root);
        totalAllocs++;
        
        if (free == null) {
            totalFails++;
            totalFailSize += b.getSize();
        } else {
            b.setAddress(free.getAddress());
            if (free.getSize() > b.getSize()) { //need to cut in two
                free.setAddress(free.getAddress() + b.getSize());
                free.setSize(free.getSize() - b.getSize()); //adjust size
                if (this.root.getData(this.root) == null) {
                    this.root = new AVLNode<Block>(free);
                } else {
                    //insert back into tree
                    //update root
                    this.root = this.root.insert(free, this.root);
                }
            }
            fullBlocks.add(b);
        }
        
        finish = System.currentTimeMillis();
        totalTime += (float) (finish - start);
        return b;
    }
    

    
    /**
     * Deallocate a block of memory, and return the block.
     * @param id the ID number of the block
     * @return the Block that has been deallocated
     */
    public Block deallocate(int id) {
        Block b = super.deallocate(id);
        if (b != null) {
            if (this.root == null) {
                this.root = new AVLNode<Block>(b);
            } else {
                //insert and updata root
                this.root = this.root.insert(b, this.root);
            }
        } 
        return b;
    }
    
    /**
     * Defragment free memory blocks.
     * @return a defragged ArrayList
     */
    public ArrayList<Block> defrag() {
        ArrayList<Block> freeBlocks;
        freeBlocks = super.defrag();
        Iterator<Block> iter = freeBlocks.iterator();
        if (iter.hasNext()) {
            this.root = new AVLNode<Block>(iter.next());
        }
        while (iter.hasNext()) {
            this.root = this.root.insert(iter.next(), this.root);
        }
        return freeBlocks;
        
    }
    
    /**
     * Return an ArrayList of the AVL tree.
     * @return an ArrayList representation
     */
    public ArrayList<Block> toArray() {
        ArrayList<Block> array = new ArrayList<Block>();
        array = this.root.toArray(this.root);
       
        return array;
    }
    
    /**
     * Return the current size of the tree.
     * @return the size of the tree
     */
    public int getSize() {
        return this.root.getSize();
    }
}
