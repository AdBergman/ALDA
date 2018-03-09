// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera.
package alda.heap;

//DHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a D-heap.
 * Note that all "matching" is based on the compareTo method.
 *
 * @author Mark Allen Weiss
 */


/**
 * Implements a D-ary heap.
 * @author Adrian Bergman adbe0777 bergman.adrian@gmail.com
 * @author Sebastian Bäckström Pino sebc5325 s.backstrompino@gmail.com
 * @author Martin Sendén mase4691 martin.senden@gmail.com
 * @since 2018-02-01
 */


public class DHeap<AnyType extends Comparable<? super AnyType>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;      // Number of elements in heap
    private AnyType[] array; // The heap array
    private int dHeapSize;
    private boolean perc = false;

    /**
     * Construct the binary heap.
     */
    public DHeap() {
        this(DEFAULT_CAPACITY);
        dHeapSize = 2;
    }



    /**
     * Construct the binary heap.
     *
     * @param capacity the capacity of the binary heap.
     */
    public DHeap(int capacity) throws IllegalArgumentException {
        if (capacity < 2){
            throw new IllegalArgumentException("Can't make a heap less than D2");
        }
        dHeapSize = capacity;
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public DHeap(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for (AnyType item : items)
            array[i++] = item;
        buildHeap();
    }

    /**
     * New Methods for D-Heap
     */

    public int parentIndex(int x) throws IllegalArgumentException {
        if (x < 2){
            throw new IllegalArgumentException("Can't check parent of root node as it does not have a parent");
        }
        return ((x-2)/dHeapSize)+1;
    }

    public int firstChildIndex(int x) throws IllegalArgumentException{
        if (x == 0) {
            throw new IllegalArgumentException();
        }
        return (((x-1) * dHeapSize)+2);
    }

    public int size() {
        return currentSize;
    }

    public AnyType get(int x) {
        return array[x];
    }

    public AnyType[] getArray(){
        return array;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     *
     * Implementation of insert method inspired by http://www.programming-algorithms.net/article/41909/D-ary-heap
     *
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        // Percolate up

        currentSize++;
        int index = currentSize;
        if (currentSize != 1){
            int parentIndex = parentIndex(index);
            while (index != 1 && x.compareTo(array[parentIndex]) < 1) {
                array[index] = array[parentIndex];
                index = parentIndex;
                if (index !=1) {
                    parentIndex = parentIndex(index);
                }
            }
        }
        array[index] = x;
 }

    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }


    /**
     * Internal method to percolate down in the heap.
     *
     * @param holeIndex the index at which the percolate begins.
     */
    private void percolateDown(int holeIndex) {
        AnyType tmp = array[holeIndex];
        while (firstChildIndex(holeIndex) <= currentSize) {

            int childIndex = firstChildIndex(holeIndex);
            AnyType smallestChild = array[firstChildIndex(holeIndex)];
            if (firstChildIndex(holeIndex) != currentSize) {
                for (int i = firstChildIndex(holeIndex); i < firstChildIndex(holeIndex) + dHeapSize - 1 && i < currentSize; i++) {
                    if (smallestChild.compareTo(array[i + 1]) > 0) {
                        smallestChild = array[i + 1];
                        childIndex = i + 1;
                    }
                }
            }

            if (smallestChild.compareTo(array[holeIndex]) < 0) {
                array[holeIndex] = smallestChild;
            } else {
                return;
            }

            holeIndex = childIndex;
            array[holeIndex] = tmp;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 1; i < currentSize; i++){
            stringBuilder.append(array[i] + ", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString() + "]";
    }

    // Test program
    public static void main(String[] args) {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems)
            h.insert(i);
        for (i = 1; i < numItems; i++)
            if (h.deleteMin() != i)
                System.out.println("Oops! " + i);
    }
}
