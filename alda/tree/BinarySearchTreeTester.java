package alda.tree;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.*;
import static org.junit.Assert.*;

public class BinarySearchTreeTester {

    private BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

    @Test
    public void testEmptyTree() {
        tree.clear();
        assertEquals(0, tree.size());
        assertEquals(-1, tree.depth());
        assertEquals("[]", tree.toString());
    }


    @Before
    public void setUp() throws Exception {
        tree.add(5);
        tree.add(4);
        tree.add(2);
        tree.add(3);
        tree.add(6);
        tree.add(1);
    }

    @Test
    public void testAddUnique() {
        for (int n = 1; n <= 6; n++) {
            assertTrue(tree.contains(n));
        }
    }

    @Test
    public void testSize() {
        assertEquals(6, tree.size());
    }

    @Test
    public void testDepth() {
        assertEquals(3, tree.depth());
    }

    @Test
    public void testToString() {
        assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testAddDuplicates() {
        for (int n = 1; n <= 6; n += 2)
            assertFalse(tree.add(n));
    }

    @Test
    public void testRemoveExistingLeaf() {
        assertTrue(tree.remove(1));
        assertEquals(5, tree.size());
        assertEquals("[2, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyRightChild() {
        assertTrue(tree.remove(4));
        assertEquals(5, tree.size());
        assertEquals("[1, 2, 3, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
        tree.add(7);
        assertTrue(tree.remove(6));
        assertEquals(6, tree.size());
        assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithTwoChildren() {
        assertTrue(tree.remove(2));
        assertEquals(5, tree.size());
        assertEquals("[1, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveRoot() {
        assertTrue(tree.remove(5));
        assertEquals(5, tree.size());
        assertEquals("[1, 2, 3, 4, 6]", tree.toString());
    }

    @Test
    public void testRandomAddAndRemove() {
        Random rnd = new Random();

        SortedSet<Integer> oracle = new TreeSet<Integer>();
        for (int n = 1; n <= 6; n++)
            oracle.add(n);

        for (int n = 0; n < 1000; n++) {
            int toAdd = rnd.nextInt(10);
            assertEquals(oracle.add(toAdd), tree.add(toAdd));
            int toRemove = rnd.nextInt(10);
            assertEquals(oracle.remove(toRemove), tree.remove(toRemove));
            int checkExists = rnd.nextInt(10);
            assertEquals(oracle.contains(checkExists), tree
                    .contains(checkExists));
            assertEquals(oracle.size(), tree.size());
            assertEquals(oracle.toString(), tree.toString());
        }
    }

    @Test
    public void testOtherType(){
        BinarySearchTree<String> stringTree = new BinarySearchTree<String>();
        stringTree.add("D");
        stringTree.add("A");
        stringTree.add("C");
        stringTree.add("A");
        stringTree.add("B");
        assertEquals(4, stringTree.size());
        assertTrue(stringTree.contains("C"));
        stringTree.remove("C");
        assertFalse(stringTree.contains("C"));
    }

    @Test
    public void testVerySimpleImplementation() {
        BinarySearchTree<Integer> tree2 = new BinarySearchTree<Integer>();
        assertTrue(tree2.add(5));
        assertTrue(tree2.add(3));
        assertTrue(tree2.add(7));
        assertTrue(tree2.add(4));
        assertTrue(tree2.add(6));
        assertTrue(tree2.add(2));
        assertTrue(tree2.add(8));

        assertEquals(7, tree2.size());
        assertTrue(tree2.add(1));
        assertEquals(8, tree2.size());

        //Check for no Duplicate
        assertFalse(tree2.add(1));
        assertEquals(8, tree2.size());

        assertTrue(tree2.contains(7));
        assertTrue(tree2.contains(8));
        assertTrue(tree2.contains(5));
        assertTrue(tree2.contains(2));
        assertFalse(tree2.contains(20));
        assertFalse(tree2.contains(0));
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", tree2.toString());

/*        tree2.remove(1);
        assertFalse(tree2.contains(1));
        tree2.remove(4);
        assertFalse(tree2.contains(4));
        tree2.remove(3);
        assertFalse(tree2.contains(3));
        assertEquals("[2, 5, 6, 7, 8]", tree2.toString());*/

        assertTrue(tree2.contains(7));
        System.out.println(tree2);
        assertTrue(tree2.remove(7));
        assertFalse(tree2.contains(7));

        System.out.println(tree2);;
        System.out.println("remove 7 again!?");
        assertFalse(tree2.remove(7));
        System.out.println(tree2);;



        //assertEquals(1, tree2.depth());


    }

    @Test
    public void testRandomAddAndRemove2() {
        Random rnd = new Random();

        SortedSet<Integer> oracle = new TreeSet<Integer>();
        for (int n = 1; n <= 6; n++)
            oracle.add(n);

        for (int n = 0; n < 1000; n++) {
            int toAdd = rnd.nextInt(10);
            assertEquals(oracle.add(toAdd), tree.add(toAdd));
            int toRemove = rnd.nextInt(10);
            Boolean a = oracle.remove(toRemove);
            Boolean b = tree.remove(toRemove);
            System.out.println("Case: " + n);
            System.out.println("To add:" + toAdd);
            System.out.println("To remove:" + toRemove);
            System.out.println( a+ ", " + b);
            System.out.println();
            assertEquals(a, b);
            int checkExists = rnd.nextInt(10);
            assertEquals(oracle.contains(checkExists), tree
                    .contains(checkExists));
            assertEquals(oracle.size(), tree.size());
            assertEquals(oracle.toString(), tree.toString());
        }
    }

}