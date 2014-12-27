import java.util.ArrayList;
import java.util.LinkedList;

/**
 * HashTable class used for CloseFit.java which extends BaseManager.
 * @param <T> base type of the
 * @author - Eddie Kong, Daniel Kim, Matt Gigliotti
 */
public class HashTable<T> {

    /** Default initial size of the hash table. */
    private static final int NUMBER_OF_BUCKETS = 10;

    /**
     * Max size of blocks. Max memory possible. This number is used in
     * calculating the range buckets should hold
     */
    private static int maxMemory;

    /**
     * Max number of buckets in our hash table. This ensures that we can only
     * have 100 elements in the table at any time and hence a load factor of
     * 0.75
     */
    private static int maxCapacity;
    
    /** Load factor of the hash table. */
    private static final double LOAD_FACTOR = 0.75;

    /** Counter to keep track of elements in the hash table. */
    private int numOfElements;

    /** The range of values that buckets hold. */
    private final int bucketRange;

    /**
     * Hash table consisting of an array list of linked lists containing T
     * objects.
     */
    private final ArrayList<LinkedList<T>> table;

    /**
     * Default constructor for HashTable. Default size is 10
     * @param mem the max memory capacity input by user
     */
    public HashTable(int mem) {
        
        maxMemory = mem;
        maxCapacity = (int) Math.ceil(maxMemory / LOAD_FACTOR); 
        //added multiplication
        this.table = new ArrayList<LinkedList<T>>(NUMBER_OF_BUCKETS);
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            this.table.add(new LinkedList<T>());
        }
        this.bucketRange = (int) Math.ceil(maxMemory / NUMBER_OF_BUCKETS);

    }

    /**
     * Constructor for HashTable.
     * @param size - number to initialize the size of the array with
     * @param mem - the max memory capacity
     */
    public HashTable(int mem, int size) {
        
        //added these two lines
        maxMemory = mem;
        maxCapacity = (int) Math.ceil(maxMemory / LOAD_FACTOR); 

        if (size > maxCapacity) {
            size = maxCapacity;
        }
        this.table = new ArrayList<LinkedList<T>>(size);
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            this.table.add(new LinkedList<T>());
        }
        this.bucketRange = (int) Math.ceil(mem / size);
    }

    /**
     * Inserts data into the hash table. Fails to do so if the table is full.
     * Otherwise calls the hash method to figure out which bucket to place the
     * data into.
     * @param block - the data type to be inserted
     */
    public void insert(T block) {

        if (this.numOfElements == maxCapacity) {
            System.out.println("Table full!");
            return;
        }
        int bucket;
        bucket = this.hash(block.hashCode());
        if (this.table.get(bucket) == null) {
            System.out.println("null");
            return;
        }
        this.table.get(bucket).add(block);
        this.numOfElements++;
    }

    /**
     * Hash function to compute the index to put the object into.
     * @param size - number of the size of the object
     * @return the corresponding index the object belongs to
     */
    public int hash(int size) {

        int index;
        index = ((size - 1) / this.bucketRange);
        return index;
    }

    /**
     * Removes an element that is close to the inputed size. Uses the hash
     * function to get to the bucket and removes the last element in the bucket.
     * Returns this data.
     * @param size the size of the data you seek
     * @return the data in the bucket associated with the size
     */
    public T remove(int size) {

        int bucket;
        bucket = this.hash(size);
        for (int a = bucket; a < this.table.size(); a++) {
            if (!this.table.get(a).isEmpty()) {
                for (int i = 0; i < this.table.get(a).size(); i++) {
                    if (this.table.get(a).get(i).hashCode() >= size) {
                        this.numOfElements--;
                        return this.table.get(a).remove(i);
                    }
                }
            }
        }

        return null;
    }

    /**
     * This method creates a new hash table of double the size (if possible).
     * Copies all the elements from old to the new one using new hash function
     * @param size the size of the new hash table
     * @return the newly created hash table with the old elements
     */
    public HashTable<T> rehash(int size) {

        final HashTable<T> newHash = new HashTable<T>(maxMemory, size * 2);
        for (int a = 0; a < size * 2; a++) {
            newHash.table.add(new LinkedList<T>());
        }
        for (int i = 0; i < this.table.size(); i++) {
            for (int j = 0; j < this.table.get(i).size(); j++) {
                newHash.insert(this.table.get(i).get(j));
            }
        }
        return newHash;
    }

    /**
     * Method to check how many elements are in the hash table.
     * @return the number of elements
     */
    public int getElements() {

        return this.numOfElements;
    }

    /**
     * Method to return the number of buckets in hash table.
     * @return the number of buckets in hash table
     */
    public int getBuckets() {

        return this.table.size();
    }
    
    /** Converts the hash table into an array list of all blocks.
     * @return the array list containing the unsorted blocks.
     */
    public ArrayList<T> toArray() {
        ArrayList<T> unorderedArray = new ArrayList<T>(this.getElements());
        for (int i = 0; i < this.getBuckets(); i++) {
            for (int j = 0; j < this.table.get(i).size(); j++) {
                unorderedArray.add(this.table.get(i).get(j));
            }
        }
        return unorderedArray;
    }

}
