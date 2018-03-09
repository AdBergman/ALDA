package alda.linear;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * @author Adrian Bergman adbe0777 bergman.adrian@gmail.com
 * @author Sebastian Backstrom Pino sebc5325 s.backstrompino@gmail.com
 * @author Martin Senden mase4691 martin.senden@gmail.com
 * @since 2018-01-19
 */
public class MyALDAList<E> implements ALDAList<E> {

    private Node<E> first;
    private Node<E> last;

    //Node class that contains data and reference to the next Node.
    private static class Node<E> {
        E data;
        Node next;

        public Node(E data) {
            this.data = data;
        }
    }

    //Adds an object to the end of the list. In case of an empty list adds the single node and sets first and last..
    @Override
    public void add(E element) {

        if (first == null) {
            first = new Node<>(element);
            last = first;
        } else {
            last.next = new Node<>(element);
            last = last.next;
        }
    }


    /**
     * <p>
     * This method creates a new Node at the index containing the data specified in the element.
     * There are four cases pertaining to how Nodes are added to the list.</p>
     * <ul>
     * <LI>If the list is empty the new Node is created. Then the first and last references are linked to it.</LI>
     * <LI>If the list is not empty and index is 0 the new Node is linked to the old first Node and then set to first.</LI>
     * <LI>If the list is not empty the Node is linked to next Node and the Node before is linked to new Node.</LI>
     * <LI>If the list is not empty and the node is created after the last node. Last is set to the new Node.</LI>
     * </ul>
     * @param index The index at which the element is to be added to the list.
     * @param element The element that is to be added to the list.
     * @throws IndexOutOfBoundsException Error handling ascertains that the index is not below zero or above size().
     */

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> newNode = new Node<>(element);

        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            int counter = 0;
            for (Node<E> temp = first; temp != null; temp = temp.next, counter++) {
                if (counter == index && index == 0) {
                    newNode.next = temp;
                    first = newNode;
                }

                if (counter + 1 == index) {
                    newNode.next = temp.next;
                    temp.next = newNode;
                    if (newNode.next == null) {
                        last = newNode;
                    }

                }
            }
        }
    }

    //Removes node which index matches the parameter or throws IndexOutOfBounds if the parameter is invalid.
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (first == null || index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        int counter = 0;
        Node<E> deletedNode;
        if (index == 0) {
            deletedNode = first;
            first = first.next;
            return deletedNode.data;
        }

        for (Node<E> temp = first; temp != null; temp = temp.next, counter++) {
            if (counter + 1 == index && temp.next == last) {
                deletedNode = temp.next;
                temp.next = null;
                last = temp;
                return deletedNode.data;
            }
            if (counter + 1 == index) {
                deletedNode = temp.next;
                temp.next = temp.next.next;
                return deletedNode.data;

            }
        }
        return null;
    }

    //Removes a node if it's data matches the parameter.
    @Override
    public boolean remove(E element) {
        if (!contains(element)) {
            return false;
        }
        if (first.data == element) {
            first = first.next;
            return true;
        }

        for (Node<E> temp = first; temp != null; temp = temp.next) {
            if (temp.next.data == element && temp.next == last) {
                temp.next = null;
                last = temp;
                return true;
            }
            if (temp.next.data == element) {
                temp.next = temp.next.next;
                return true;

            }
        }
        return false;
    }

    //Returns the data of the Node with an index matching the parameter.
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }

        int counter = 0;

        for (Node<E> temp = first; temp != null; temp = temp.next, counter++) {
            if (counter == index) {
                return temp.data;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    //This method checks if there is a Node matching the parameter.
    @Override
    public boolean contains(E element) {

        for (Node<E> temp = first; temp != null; temp = temp.next) {
            if (temp.data == element || temp.data.equals(element)) {
                return true;
            }
        }
        return false;
    }

    //This method checks if there is a Node matching the parameter and returns the index of the Node.
    @Override
    public int indexOf(E element) {
        int counter = 0;

        for (Node<E> temp = first; temp != null; temp = temp.next, counter++) {
            if (temp.data == element || temp.data.equals(element)) {
                return counter;
            }
        }

        return -1;
    }

    //This method resets the list.
    @Override
    public void clear() {
        first = null;
        last = null;

    }

    //This method returns the size of the list.
    @Override
    public int size() {
        int counter = 0;

        for (Node<E> temp = first; temp != null; temp = temp.next, counter++) {

        }
        return counter;
    }

    //To string to display the data of all nodes.
    @Override
    public String toString() {
        String tmpString = "[";

        for (Node<E> temp = first; temp != null; temp = temp.next) {
            if (temp.next != null) {
                tmpString += temp.data.toString() + ", ";
            } else {
                tmpString += temp.data.toString();
            }
        }

        return tmpString + "]";
    }


    @Override
    public ALDAIterator iterator() {
        return new ALDAIterator();
    }

    private class ALDAIterator implements Iterator {

        private Node<E> currentNode = first;
        private Node<E> previousNode;
        private boolean removable = false;


        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() throws NoSuchElementException {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            previousNode = currentNode;
            currentNode = currentNode.next;
            removable = true;
            return previousNode.data;
        }

        @Override
        public void remove() throws IllegalStateException {

            if (!removable) {
                throw new IllegalStateException();
            }

            MyALDAList.this.remove(previousNode.data);
            removable = false;
        }
    }
}


