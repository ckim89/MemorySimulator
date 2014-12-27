import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class MaxHeapTest {
    MaxHeap<Block> firstheap;

    @Before
    public void setUp() {
        this.firstheap = new MaxHeap<Block>();
    }

    @Test
    public void emptyTest() {
        Block newblock = new Block(1, 0, 10);
        assertTrue(this.firstheap.isEmpty());
        this.firstheap.insert(newblock);
        assertFalse(this.firstheap.isEmpty());
    }
    
    @Test
    public void sizeTest() {
        Block newblock = new Block(1, 0, 10);
        assertTrue(this.firstheap.size() == 0);
        this.firstheap.insert(newblock);
        assertTrue(this.firstheap.size() == 1);
        this.firstheap.insert(newblock);
        assertTrue(this.firstheap.size() == 2);
        this.firstheap.deleteMax();
        assertTrue(this.firstheap.size() == 1);
    }
    @Test
    public void maxTest() {
        for (int i = 0; i < 5; i++) {
            Block newblocks = new Block(i, i, i * i);
            this.firstheap.insert(newblocks);
            assertTrue(this.firstheap.max().getSize() == i * i);
        }
        assertTrue(this.firstheap.max().getSize() == 16);
    }
    
    @Test
    public void insertTest() {
        List<Block> heaptest;
        for (int i = 0; i < 5; i++) {
            Block newblocks = new Block(i, i, i*2);
            this.firstheap.insert(newblocks);
        }
        heaptest = this.firstheap.getHeap();
        assertTrue(this.firstheap.max().getSize() == 8);
        assertTrue(this.firstheap.getHeap().get(2).getSize() == 6);
        assertTrue(this.firstheap.getHeap().get(3).getSize() == 2);
        assertTrue(this.firstheap.getHeap().get(4).getSize() == 0);
        assertTrue(this.firstheap.getHeap().get(5).getSize() == 4);
    }
    
    @Test
    public void deleteTest() {
        for (int i = 0; i < 5; i++) {
            Block newblocks = new Block(i, i, i*2);
            this.firstheap.insert(newblocks);
        }
        this.firstheap.deleteMax();
        assertTrue(this.firstheap.max().getSize() == 6);
        assertTrue(this.firstheap.getHeap().get(2).getSize() == 4);
        assertTrue(this.firstheap.getHeap().get(3).getSize() == 2);
        assertTrue(this.firstheap.getHeap().get(4).getSize() == 0);
    }

}
