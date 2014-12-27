import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class AVLNodeTest {
    AVLNode<Block> newtree;
    Block newblock = new Block(0, 0, 10);
    Block newblock1 = new Block(1, 1, 15);
    Block newblock2 = new Block(2, 129, 30);
    Block newblock3 = new Block(3, 192, 40);
    
    @Before
    public void setUp() {
        this.newtree = new AVLNode<Block>(this.newblock);
    }

    @Test
    public void testSize() {     
        assertTrue(this.newtree.getSize() == 1);
        this.newtree.insert(newblock1, this.newtree);
        assertTrue(this.newtree.getSize() == 2);
    }
    
    @Test
    public void testInsert() {
        ArrayList<Block> newlist;
        this.newtree = this.newtree.insert(newblock1, this.newtree);
        this.newtree = this.newtree.insert(newblock1, this.newtree);
        this.newtree = this.newtree.insert(newblock2, this.newtree);
        newlist = this.newtree.toArray(this.newtree);
        assertTrue(newlist.get(0).getSize() == 10);
        assertTrue(newlist.get(1).getSize() == 15);
        assertTrue(newlist.get(2).getSize() == 15);
        assertTrue(newlist.get(3).getSize() == 30);      
    }
    
    @Test
    public void testRemove() {
        ArrayList<Block> newlist;
        this.newtree = this.newtree.insert(newblock1, this.newtree);
        this.newtree = this.newtree.insert(newblock2, this.newtree);
        this.newtree = this.newtree.insert(newblock3, this.newtree);
        this.newtree = this.newtree.remove(newblock3, this.newtree);
        this.newtree = this.newtree.remove(newblock2, this.newtree);
        newlist = this.newtree.toArray(this.newtree);
        assertTrue(newlist.get(0).getSize() == 10);
        assertTrue(newlist.get(1).getSize() == 15);       
    }
    
    @Test
    public void testBestfit() {
        Block newestblock = new Block(4, 3, 9);
        Block newesterblock = new Block(5, 19, 14);
        this.newtree = this.newtree.insert(newblock1, this.newtree);
        this.newtree = this.newtree.insert(newblock2, this.newtree);
        this.newtree = this.newtree.insert(newblock3, this.newtree);
        assertEquals(this.newtree.removeBestFit(newestblock, this.newtree), newblock);
        assertEquals(this.newtree.removeBestFit(newesterblock, this.newtree), newblock1);
    }

}
