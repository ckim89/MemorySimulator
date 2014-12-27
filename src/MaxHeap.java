import java.util.List;
import java.util.ArrayList;

/**
 * This class is the implementation of the priority queue.
 * @author Matt Gigliotti, Eddie Kong, Dan Kim
 * @param <T> is the base object put into the heap.
 */
public class MaxHeap<T extends Comparable<T>> {
    
    /**
     * This creates a representation of the heap in array form.
     */
    private List<T> heap;
    
    /**
     * This is the constructor for the PriorityQueue class.
     */
    public MaxHeap() {
        this.heap = new ArrayList<T>();    
        this.heap.add(null);
    }
    
    /**
     * This method returns the size of the queue.
     * @return the size of the priority queue.
     */
    public int size() {
        return this.heap.size() - 1;
    }
    
    /**
     * This method sees if the queue is empty or not.
     * @return true if empty, false if not.
     */
    public boolean isEmpty() {
        return this.heap.size() == 1;
    }
    
    /**
     * This method adds blocks to the priority queue.
     * @param emptyblock is the empty block being added.
     */
    public void insert(T emptyblock) {
        T mom;
        T child;
        this.heap.add(emptyblock);
        int curr = this.size();
        int parent = curr / 2;
        mom = this.heap.get(parent);
        child = this.heap.get(curr);
        if (mom == null || child == null) {
            return;
        }
        while (parent > 0 && (mom.compareTo(child) < 0)) {
            this.heap.set(parent, child);
            this.heap.set(curr, mom);
            curr = parent;
            parent = parent / 2;
            mom = this.heap.get(parent);
            child = this.heap.get(curr);
            if (mom == null || child == null) {
                return;
            }
        }
    }
    
    /**
     * This method deletes the max block in the heap.
     */
    public void deleteMax() {
        int curr, max, left, right;
        T last;
        max = 0;
        curr = 1;
        left = curr * 2;
        right = left + 1;
        if (this.isEmpty()) {
            return;
        }
        last = this.heap.get(this.size());
        this.heap.remove(this.size());
        if (this.isEmpty()) {
            return;
        }
        this.heap.set(curr, last);
        while (right <= this.size()) {
            if (last.compareTo(this.heap.get(left)) > 0) {
                if (last.compareTo(this.heap.get(right)) > 0) {
                    return;
                }
            }
            if (this.heap.get(left).compareTo(this.heap.get(right)) >= 0) {
                max = left;
            } else {
                max = right;
            }
            this.heap.set(curr, this.heap.get(max));
            this.heap.set(max, last);
            curr = max;
            left = curr * 2;
            right = left + 1;
        }
    }
    
    /**
     * This method returns the block with the highest priority.
     * @return the block with the largest amount of free space.
     */
    public T max() {
        try {
            return this.heap.get(1); 
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
      
    }
    
    /**
     * gets the heap.
     * @return the heap
     */
    public List<T> getHeap() {
        return this.heap;
    }
    
    /**
     * This method creates a new array of empty blocks from the heap.
     * @return the new array made from the structure.
     */
    public ArrayList<T> toArray() {
        ArrayList<T> unorderedArray = new ArrayList<T>(this.size());
        for (int i = 1; i <= this.size(); i++) {
            unorderedArray.add(this.heap.get(i));
        }
        return unorderedArray;
    }
}
