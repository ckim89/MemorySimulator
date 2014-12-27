

/**
 * The Block class for assignment 4.
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 */
public class Block implements Comparable<Block> {

    /**
     * The ID number.
     */
    private int id;
    /**
     * The memory address.
     */
    private int address;
    /**
     * The block size.
     */
    private int size;
    
    /**
     * Construct a block object with all values initialized to 0.
     */
    public Block() {
        this.id = 0;
        this.address = -1;
        this.size = 0;
    }
    
    /**
     * Construct a block object with parameterized fields.
     * @param d the ID number
     * @param add the memory address
     * @param s the block size
     */
    public Block(int d, int add, int s) {
        this.id = d;
        this.address = add;
        this.size = s;
    }
    
    /**
     * Set the ID number of a block.
     * @param d the new ID number
     */
    public void setID(int d) {
        this.id = d;
    }
    
    /**
     * Set the memory address of a block.
     * @param add the new memory address
     */
    public void setAddress(int add) {
        this.address = add;
    }
    
    /**
     * Set the size of a block.
     * @param s the new block size
     */
    public void setSize(int s) {
        this.size = s;
    }
    
    /**
     * Return the ID number of the block.
     * @return the ID number
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Return the memory address of the block.
     * @return the memory address
     */
    public int getAddress() {
        return this.address;
    }
    
    /**
     * Return the size of the block.
     * @return the block size
     */
    public int getSize() {
        return this.size;
    }
    
    /** 
     * Overrides the compareTo method of the Comparable interface.
     * @param b the block to compare
     * @return 1 if this has a greater block size, -1 if this has a smaller 
     * block size, and 0 if the block sizes are equal
     */
    public int compareTo(Block b) {
        
        if (this.size > b.getSize()) {
            return 1;
        } else if (this.size < b.getSize()) {
            return -1;
        } else {
            return 0;
        }
    }
    
    /** 
     * Overrides the hashCode method of the Object class, for use 
     * in the HashTable class.
     * @return the size of the block 
     */
    public int hashCode() {
        return this.size;
    }
}
