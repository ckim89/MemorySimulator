import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class HashTableTest {
    HashTable<Block> newtable;
    HashTable<Block> newtable1;
    Block newblock1;
    Block newblock2;
    Block newblock3;
    Block newblock4;
    Block newblock5;
    
    @Before
    public void setUp() {
        newtable = new HashTable<Block>(100);
        newtable1 = new HashTable<Block>(100);
        newblock1 = new Block(0, 0, 10);
        newblock2 = new Block(1, 10, 15);
        newblock3 = new Block(2, 20, 35);
        newblock4 = new Block(3, 30, 34);
        newblock5 = new Block(4, 50, 1);
    }
    
    @Test 
    public void testEmpty() {
        assertTrue(this.newtable.getElements() == 0);
        this.newtable.insert(newblock1);
        this.newtable.insert(newblock2);
        assertTrue(this.newtable.getElements() != 0);
    }

    @Test
    public void testRehash() {
        HashTable<Block> rehashedtable;
        assertTrue(this.newtable.getBuckets() == 10);
        rehashedtable = this.newtable.rehash(200);
        assertTrue(rehashedtable.getBuckets() == 20);
    }
    
    @Test
    public void testInsert() {
        ArrayList<Block> testArray;
        ArrayList<Block> compareArray = new ArrayList<Block>();
        this.newtable.insert(newblock1);
        this.newtable.insert(newblock2);
        this.newtable.insert(newblock3);
        compareArray.add(newblock1);
        compareArray.add(newblock2);
        compareArray.add(newblock3);
        assertTrue(this.newtable.getElements() == 3);
        testArray = this.newtable.toArray();
        assertTrue(testArray.equals(compareArray));
    }
    
    @Test
    public void testBuckets() {
        ArrayList<LinkedList<Block>> testtable;
        LinkedList<Block> testbucket1;
        LinkedList<Block> testbucket2;
        this.newtable.insert(newblock3);
        this.newtable.insert(newblock5);
        testtable = this.newtable.getTable();
        assertTrue(testtable.get(0).get(0).getSize() == 1);
        assertTrue(testtable.get(3).get(0).getSize() == 35);
        this.newtable.insert(newblock1);
        testtable = this.newtable.getTable();
        assertTrue(testtable.get(0).get(1).getSize() == 10);
        
    }
    
    @Test
    public void testDelete() {
        Block testblock;
        this.newtable.insert(newblock1);
        this.newtable.insert(newblock2);
        this.newtable.insert(newblock3);
        this.newtable.insert(newblock4);
        testblock = this.newtable.remove(17);
        assertTrue(this.newtable.getElements() == 3);
        assertTrue(testblock.getSize() == 35);
        this.newtable1.insert(newblock1);
        this.newtable1.insert(newblock2);
        this.newtable1.insert(newblock4);
        this.newtable1.insert(newblock3);
        testblock = this.newtable1.remove(17);
        assertTrue(this.newtable1.getElements() == 3);
        assertTrue(testblock.getSize() == 34);
        
    }

}
