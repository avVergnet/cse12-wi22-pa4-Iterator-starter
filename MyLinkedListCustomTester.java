
/**
 * TODO: Add your file header
 * Name:
 * ID:
 * Email:
 * Sources used: Put "None" if you did not have any external help
 * Some example of sources used would be Tutors, Zybooks, and Lecture Slides
 * 
 * 2-4 sentence file description here
 */

import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.NoSuchElementException;

import org.junit.*;

/**
 * TODO: Add your class header
 * 
 * IMPORTANT: Do not change the method headers and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class MyLinkedListCustomTester {

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    
    private MyLinkedList emptyMLL, intMLL;
    private MyLinkedList.MyListIterator iter1, iter2;

    @Before
    public void setUp() throws Exception {
        emptyMLL = new MyLinkedList<>();
        iter1 = emptyMLL.new MyListIterator();

        intMLL = new MyLinkedList<Integer>();
        intMLL.add(9);
        intMLL.add(8);
        intMLL.add(7);
        iter2 = intMLL.new MyListIterator();
    }

    // ---------------------- hasNext() -----------------------------------

    /**
     * Test the hasNext method when called on empty MyLinkedList
     */
    @Test
    public void testHasNext() {
        assertFalse(iter1.hasNext());
    }
    @Test
    public void testHasNext2(){
        assertTrue(iter2.hasNext());
        iter2.next();
        assertTrue(iter2.hasNext());
        iter2.next();
        assertTrue(iter2.hasNext());
        iter2.next();
        assertFalse(iter2.hasNext());
    }

    // ---------------------- next() -----------------------------------
    /**
     * Test the next method when called on empty MLL
     */
    @Test(expected = NoSuchElementException.class)
    public void testNext() {
        try{
            iter1.next();
        }catch(NoSuchElementException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the next method when called at the end of the list
     */
    @Test(expected = NoSuchElementException.class)
    public void testNext2() {
        iter2.next();
        iter2.next();
        iter2.next(); //moving to the end of the list
        try{
            iter2.next();
        }catch(NoSuchElementException e){
            throw e;
        }
        fail("Exception not catched");
    }
    @Test
    public void testNext3() {
        assertEquals(9, iter2.next());
        assertEquals(8, iter2.next());
        assertEquals(7, iter2.next());
    }

    // ---------------------- hasPrevious() --------------------------------

    /**
     * Test the hasPrevious method when called on an empty MLL
     */
    @Test
    public void testHasPrevious() {
        assertFalse(iter1.hasPrevious());
    }
    @Test
    public void testHasPrevious2() {
        assertFalse(iter2.hasPrevious());
        iter2.next();
        assertTrue(iter2.hasPrevious());
        iter2.next();
        assertTrue(iter2.hasPrevious());
        iter2.next();
        assertTrue(iter2.hasPrevious());
    }

    // ---------------------- previous() --------------------------------

    /**
     * Test the previous method when called on empty MLL
     */
    @Test(expected = NoSuchElementException.class)
    public void testPrevious() {
        try{
            iter1.previous();
        }catch(NoSuchElementException e){
            throw e;
        }
        fail("Exception not catched");
    }
    @Test(expected = NoSuchElementException.class)
    public void testPrevious2(){
        iter2.next();
        iter2.next();
        iter2.next(); //moving to the end of the list
        iter2.previous();
        iter2.previous();
        iter2.previous(); //moving back to the beginnin
        try{
            iter2.previous();
        }catch(NoSuchElementException e){
            throw e;
        }
        fail("Exception not catched");
    }
    @Test
    public void testPrevious3() {
        iter2.next();
        iter2.next();
        iter2.next(); //moving to the end of the list
        assertEquals(7, iter2.previous());
        assertEquals(8, iter2.previous());
        assertEquals(9, iter2.previous());
    }

    // ---------------------- nextIndex() --------------------------------

    /**
     * Test the nextIndex method when called on an empty list
     */
    @Test
    public void testNextIndex() {
        assertEquals(0, iter1.nextIndex());
    }
    /**
     * Test the nextIndex method when called on an at the end of list and 
     * should return size and check every step along moving up
     */
    @Test
    public void testNextIndex2() {
        assertEquals(0, iter2.nextIndex());
        iter2.next();
        assertEquals(1, iter2.nextIndex());
        iter2.next();
        assertEquals(2, iter2.nextIndex());
        iter2.next(); //moving to the end of the list
        assertEquals(3, iter2.nextIndex());
    }

    // ---------------------- previousIndex() ------------------------------

    /**
     * TODO: test the previousIndex method when [...]
     */
    @Test
    public void testPreviousIndex() {
        assertEquals(-1, iter1.previousIndex());
    }
    /**
     * Test the previousIndex method when called on an at the begining of list
     * and return -1 and check every step along moving up
     */
    @Test
    public void testPreviousIndex2() {
        assertEquals(-1, iter2.previousIndex());
        iter2.next();
        assertEquals(0, iter2.previousIndex());
        iter2.next();
        assertEquals(1, iter2.previousIndex());
        iter2.next();
        assertEquals(2, iter2.previousIndex());
    }

    // ---------------------- set() ------------------------------

    /**
     * Test the set method when data is null
     */
    @Test(expected = NullPointerException.class)
    public void testSet() {
        iter2.next();
        try{
            iter2.set(null);
        }catch(NullPointerException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the set method when canRemoveOrSet is false from begining
     */
    @Test(expected = IllegalStateException.class)
    public void testSet2() {
        try{
            iter2.set(9);
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the set method when canRemoveOrSet is false because remove 
     * was just called
     */
    @Test(expected = IllegalStateException.class)
    public void testSet3() {
        iter2.next();
        iter2.add(18);
        try{
            iter2.set(91);
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the set method for going foward and backwards
     */
    @Test
    public void testSet4() {
        iter2.next();
        iter2.set(55);
        assertEquals(55, iter2.left.getElement());
        iter2.next();
        iter2.set(29);
        assertEquals(29, iter2.left.getElement());
        iter2.previous();
        iter2.set(34);
        assertEquals(34, iter2.right.getElement());
    }
    /**
     * Test the set method when called on empty MLL
     */
    @Test(expected = IllegalStateException.class)
    public void testSet5() {
        try{
            iter1.set(91);
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }

    // ---------------------- remove() ------------------------------

    /**
     * Test the remove method when removing is not allowed from begining
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTestOne() {
        try{
            iter2.remove();;
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }
     /**
     * Test the remove method when removing is not allowed because add was just 
     * called
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTestTwo() {
        iter2.next();
        iter2.add(4);
        try{
            iter2.remove();
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the remove method when calling remove twice in a row
     */
    @Test(expected = IllegalStateException.class)
    public void testRemoveTestThree() {
        iter2.next();
        iter2.remove();
        try{
            iter2.remove();
        }catch(IllegalStateException e){
            throw e;
        }
        fail("Exception not catched");
    }
     /**
     * Test the remove method when having called set beforehand
     */
    @Test
    public void testRemoveTestFour() {
        iter2.next();
        iter2.set(4);
        assertEquals(4, iter2.previous());
        assertEquals(3, intMLL.size());
        iter2.remove();
        assertEquals(2, intMLL.size());
    }

    // ---------------------- add() ------------------------------

    /**
     * Test the add method when data is null
     */
    @Test(expected = NullPointerException.class)
    public void testAdd() {
        iter2.next();
        try{
            iter2.add(null);
        }catch(NullPointerException e){
            throw e;
        }
        fail("Exception not catched");
    }
    /**
     * Test the add method when called on empty list
     */
    @Test
    public void testAdd2() {
        iter1.add(66);
        assertEquals(1, emptyMLL.size());
        assertEquals(66, iter1.left.getElement());
        assertNull(iter1.right.getElement());
        assertEquals(66, iter1.previous());
    }
    /**
     * Test the add method when called at the end of MLL
     */
    @Test
    public void testAdd3() {
        iter2.next();
        iter2.next();
        iter2.next();
        iter2.add(45);
        assertEquals(45, intMLL.tail.getPrev().getElement());
        assertEquals(45, iter2.previous());
        assertEquals(7, iter2.previous());
        assertEquals(8, iter2.previous());
        assertEquals(9, iter2.previous());
    }
    /**
     * Test the add method when called multiple times
     */
    @Test
    public void testAdd4() {
        for(int i = 0; i < 1000; i++){
            iter1.add(i);
        }
        assertEquals(1000, emptyMLL.size());
        assertEquals(0, emptyMLL.head.getNext().getElement());
        assertEquals(999, iter1.previous());
        assertEquals(998, iter1.previous());
    }

}