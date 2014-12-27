import java.util.ArrayList;



/**
 * The Sorter class, which contains static methods for performing
 * Quicksort and Bucketsort on an ArrayList of Blocks.
 * 
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 *
 */
public final class Sorter {
    
    /** Number of buckets to use for bucket sort. */
    private static final int NUM_BUCKETS = 10;
    
    /** Default bucket range. */
    private static final int BUCKET_RANGE = 10;
    
    /** Number of buckets to use for bucket sort. */
    private static int numBuckets;
    
    /**
     * Dummy constructor, not to be used.
     */
    private Sorter() {
     //empty
    }
    
    
    /**
     * Sort an ArrayList of Blocks using Quicksort.This implementation is
     * based off of the description of Quicksort on Wikipedia.
     * @param array the ArrayList to sort
     * @param i the index of the leftmost element
     * @param k the index of the rightmost element
     */
    public static void quickSort(ArrayList<Block> array, int i, int k) {
        
        array.trimToSize();
        if (i < k) {
            int p = partition(array, i, k);
            quickSort(array, i, p - 1);
            quickSort(array, p + 1, k);
        }
    }
    
    /**
     * Partition a subsection of an ArrayList of Blocks.
     * @param array the array to partition
     * @param left the index of the leftmost element
     * @param right the index of the rightmost element
     * @return the index of the partitioned element
     */
    public static int partition(ArrayList<Block> array, int left, int right) {

        int pivotIndex = choosePivot(array, left, right);
        int pivotValue = array.get(pivotIndex).getAddress();
        swap(array, pivotIndex, right);
        int storeIndex = left;
        
        for (int i = left; i <= right - 1; i++) {
            if (array.get(i).getAddress() <= pivotValue) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        
        swap(array, storeIndex, right);
        return storeIndex;
        
    }
    
    /**
     * Choose the index of the pivot by finding median of three.
     * @param array the array to choose pivot from
     * @param l the index of the leftmost element
     * @param r the index of the rightmost element
     * @return the index of the pivot
     */
    public static int choosePivot(ArrayList<Block> array, int l, int r) {
        Block left = array.get(l);
        Block right = array.get(r);
        Block mid = array.get((r - l) / 2);
        int median = Math.min(left.getAddress(), Math.max(right.getAddress(), 
                mid.getAddress()));
        
        if (left.getAddress() == median) {
            return l;
        } else if (mid.getAddress() == median) {
            return (r - l) / 2;
        } else {
            return r;
        }


    }
    
    /**
     * Swap two elements in an array.
     * @param array the array to perform the swap in
     * @param a the index of the first element
     * @param b the index of the second element
     */
    public static void swap(ArrayList<Block> array, int a, int b) {
        Block temp = array.get(a);
        array.set(a, array.get(b));
        array.set(b, temp);
    }
    
    /** Method to do a bucketSort on an ArrayList of blocks.
     * @param blockArray array to be sorted
     * @param mem the max memory of the memory simulator
     */
    public static void bucketSort(ArrayList<Block> blockArray, int mem) {
        numBuckets = mem / BUCKET_RANGE;
        ArrayList<ArrayList<Block>> sorty = 
                new ArrayList<ArrayList<Block>>(numBuckets);
        
        for (int a = 0; a < numBuckets; a++) {
            sorty.add(new ArrayList<Block>());
        }
        for (int i = 0; i < blockArray.size(); i++) {
            sorty.get(hash(blockArray.get(i).getAddress())).add(
                    blockArray.get(i));
        }
        
        for (int j = 0; j < numBuckets; j++) {
            sortBuckets(sorty.get(j));
        }
        
        blockArray.clear();
        
        for (int k = 0; k < numBuckets; k++) {
            for (int m = 0; m < sorty.get(k).size(); m++) {
                blockArray.add(sorty.get(k).get(m));
            }
        }
    }
    
    /** Returns the number of the bucket the block should go into. 
     * @param address - the address of the bucket
     * @return the index of the bucket the block goes into
     * */
    public static int hash(int address) {
        int bucket = (address / NUM_BUCKETS);
        return bucket;
    }
    
    /** Method for an insertion sort on a bucket.
     * @param bucket bucket on which we do insertionSort
     */
    public static void sortBuckets(ArrayList<Block> bucket) {
        Block temp;
        for (int i = 1; i < bucket.size(); i++) {
            int j = i;
            while (j > 0 && bucket.get(j - 1).getAddress() 
                    > bucket.get(j).getAddress()) {
                temp = bucket.get(j);
                bucket.set(j, bucket.get(j - 1));
                bucket.set(j - 1, temp);
                j--;
            }
        }
    }


    
}