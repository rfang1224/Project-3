package project3;

import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * This is a class representing an MDeque with a doubly linked list
 *
 * @author Robert Fang
 * @version 10/27/2025
 */

public class MDeque<E> implements Iterable<E>{

    //first node of the linked list
    private Node front;

    //last node of the linked list
    private Node back;

    //middle node of the linked list
    private Node middle;

    //length of the linked list
    private int length;

    //Iterator class that iterates through each node of the linked list starting from the head
    //Can be used to go in the opposite direction in the constructor
    private class MDequeIterator implements Iterator<E>{

        //current node that the iterator is on
        private Node current;

        //boolean determining if the list goes in reverse or not
        private boolean rev;

        //creates a new MDeque Iterator
        public MDequeIterator(boolean reverse){
            if(!reverse) current = front;
            else current = back;
            rev = reverse;
        }

        //determines if there is a node after current
        public boolean hasNext(){
            return current != null;
        }

        //returns the value stored in the current node and advances it to the next
        public E next() {
            if(!rev){
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.getVal();
                current = current.getNext();
                return value;
            }
            else{
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = current.getVal();
                current = current.getPrev();
                return value;
            }
        }
    }

    /**
     * Returns an iterator over the elements in this mdeque in proper sequence.
     * The elements will be returned in order from front to back.
     *
     * @return iterator over the elements in this mdeque in proper sequence.
     * The elements will be returned in order from front to back.
     */
    @Override
    public Iterator<E> iterator(){
        return new MDequeIterator(false);
    }

    /**
     * Returns an iterator over the elements in this mdeque in reverse sequential order.
     * The elements will be returned in order from back to front.
     *
     * @return iterator over the elements in this mdeque in reverse sequential order.
     * The elements will be returned in order from back to front.
     */
    public Iterator<E> reverseIterator(){
        return new MDequeIterator(true);
    }

    //Node class that represents each individual node within the MDeque
    private class Node{
        //the value that is stored in the node
        private E value;
        //the reference of the next node
        private Node next;
        //the reference of the previous node
        private Node prev;

        //constructs a new Node given the value, the next node, and the previous node
        public Node(E v, Node n, Node p){
            value = v;
            next = n;
            prev = p;
        }

        //sets the next Node equal to the argument node
        public void setNext(Node newNext){
            next = newNext;
        }

        //sets the previous Node equal to the argument node
        public void setPrev(Node newPrev){
            prev = newPrev;
        }

        //returns the next node
        public Node getNext(){
            return next;
        }

        //returns the previous node
        public Node getPrev(){
            return prev;
        }

        //returns the stored value
        public E getVal(){
            return value;
        }
    }

    /**
     * Creates an empty MDeque object.
     */
    public MDeque(){
        front = null;
        back = null;
        middle = null;
        length = 0;
    }

    /**
     * Retrieves the back element of this mdeque.
     *
     * @return the back of this mdeque, or null if this mdeque is empty
     */
    public E peekBack(){
        if(back == null){
            return null;
        }
        return back.getVal();
    }

    /**
     * Retrieves the first element of this mdeque.
     *
     * @return the front of this mdeque, or null if this mdeque is empty
     */
    public E peekFront(){
        if(front == null){
            return null;
        }
        return front.getVal();
    }

    /**
     * Retrieves the middle element of this mdeque.
     *
     * @return the middle of this mdeque, or null if this mdeque is empty
     */
    public E peekMiddle(){
        if(middle == null){
            return null;
        }
        return middle.getVal();
    }

    /**
     * Retrieves and removes the back element of this mdeque.
     *
     * @return the back of this mdeque, or null if this mdeque is empty
     */
    public E popBack(){
        // if length is 0 there is nothing to pop
        if(length == 0){
            return null;
        }
        // if length is 1 then the list is emptied and the value of back is returned
        else if(length == 1){
            E temp = back.getVal();
            front = null;
            back = null;
            middle = null;
            length--;
            return temp;
        }
        // if length is even, remove back and move middle left (maintains right-middle)
        else if(length % 2 == 0){
            E temp = back.getVal();
            back.getPrev().setNext(null);
            back = back.getPrev();
            middle = middle.getPrev();
            length--;
            return temp;
        }
        // if length is odd, remove back (middle stays same)
        else{
            E temp = back.getVal();
            back.getPrev().setNext(null);
            back = back.getPrev();
            length--;
            return temp;
        }
    }

    /**
     * Retrieves and removes the first element of this mdeque.
     *
     * @return the front of this mdeque, or null if this mdeque is empty
     */
    public E popFront(){
        //if length is 0 there is nothing to pop
        if(length == 0){
            return null;
        }
        //if length is 1 the MDeque is emptied and the value of front is returned
        else if(length == 1){
            E temp = front.getVal();
            front = null;
            back = null;
            middle = null;
            length--;
            return temp;
        }
        //if length is even, remove front (middle stays same)
        else if(length % 2 == 0){
            E temp = front.getVal();
            front.getNext().setPrev(null);
            front = front.getNext();
            length--;
            return temp;
        }
        //if length is odd, remove front and move middle right (maintains right-middle)
        else{
            E temp = front.getVal();
            front.getNext().setPrev(null);
            front = front.getNext();
            middle = middle.getNext();
            length--;
            return temp;
        }
    }

    /**
     * Retrieves and removes the middle element of this mdeque.
     *
     * @return the middle of this mdeque, or null if this mdeque is empty
     */
    public E popMiddle(){
        // if length is 0 there is nothing to pop
        if(length == 0){
            return null;
        }
        //if length is 1 the MDeque is emptied and the value of middle is returned
        else if(length == 1){
            E temp = middle.getVal();
            front = null;
            back = null;
            middle = null;
            length--;
            return temp;
        }
        //if length is 2 remove the middle node, return its value, and set middle to front
        else if(length == 2){
            E temp = middle.getVal();
            if(middle == front) {
                // Middle is front, so remove front and set middle to back
                front = back;
                middle = back;
            } else {
                // Middle is back, so remove back and set middle to front
                back = front;
                middle = front;
            }
            front.setNext(null);
            front.setPrev(null);
            length--;
            return temp;
        }
        //if length is 3 remove middle and set middle to back (right-middle for length 2)
        else if(length == 3){
            E temp = middle.getVal();
            Node before = middle.getPrev();
            Node after = middle.getNext();
            if (before != null) before.setNext(after);
            if (after != null) after.setPrev(before);
            // Set middle to the back element (right-middle for length 2)
            if (after != null) {
                middle = after; // Middle was not the last element
            } else {
                middle = before; // Middle was the last element
            }
            length--;
            return temp;
        }
        //if length is even remove middle and set middle to previous node
        else if(length % 2 == 0){
            E temp = middle.getVal();
            Node before = middle.getPrev();
            Node after = middle.getNext();
            if (before != null) before.setNext(after);
            if (after != null) after.setPrev(before);
            middle = before;
            length--;
            return temp;
        }
        // if length is odd remove middle and set middle to next node
        else{
            E temp = middle.getVal();
            Node before = middle.getPrev();
            Node after = middle.getNext();
            if (before != null) before.setNext(after);
            if (after != null) after.setPrev(before);
            middle = after;
            length--;
            return temp;
        }
    }

    /**
     * Inserts the specified item at the back of this mdeque.
     *
     * @param item - the element to add
     *
     * @throws IllegalArgumentException - if item is null
     */
    public void pushBack(E item) {
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        //if length is 0, set all references to the new node
        if(length == 0){
            Node newBack = new Node(item, null, null);
            front = newBack;
            back = newBack;
            middle = newBack;
            length++;
        }
        //if length is odd, add to back and move middle right (maintains right-middle)
        else if(length % 2 == 1){
            Node newBack = new Node(item, null, back);
            back.setNext(newBack);
            back = newBack;
            middle = middle.getNext();
            length++;
        }
        //if length is even, add to back (middle stays same)
        else{
            Node newBack = new Node(item, null, back);
            back.setNext(newBack);
            back = newBack;
            length++;
        }
    }

    /**
     * Inserts the specified item at the front of this mdeque.
     *
     * @param item - the element to add
     *
     * @throws IllegalArgumentException - if item is null
     */
    public void pushFront(E item) {
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        //if length is 0, set all references equal to the new node
        if(length == 0){
            Node newFront = new Node(item, null, null);
            front = newFront;
            back = newFront;
            middle = newFront;
            length++;
        }
        //if length is odd, add to front (middle stays same)
        else if(length % 2 == 1){
            Node newFront = new Node(item, front, null);
            front.setPrev(newFront);
            front = newFront;
            length++;
        }
        //if length is even, add to front and move middle left (maintains right-middle)
        else{
            Node newFront = new Node(item, front, null);
            front.setPrev(newFront);
            front = newFront;
            middle = middle.getPrev();
            length++;
        }
    }


    /**
     * Inserts the specified item in the middle of this mdeque.
     *
     * @param item - the element to add
     *
     * @throws IllegalArgumentException - if item is null
     */
    public void pushMiddle(E item){
        if(item == null){
            throw new IllegalArgumentException("item cannot be null");
        }
        //if length is 0 then set all references to the new node
        if(length == 0){
            Node newMid = new Node(item, null, null);
            front = newMid;
            back = newMid;
            middle = newMid;
            length++;
        }
        // if length is 1 then add the new node after the current node
        else if(length == 1){
            Node newMid = new Node(item, null, middle);
            middle.setNext(newMid);
            back = newMid;
            middle = newMid;
            length++;
        }
        // if length is odd then insert after middle, middle moves right
        else if(length % 2 == 1){
            Node after = middle.getNext();
            Node newMid = new Node(item, after, middle);
            middle.setNext(newMid);
            if (after != null) after.setPrev(newMid);
            middle = newMid;
            if (middle.getNext() == null) back = middle;
            length++;
        }
        // if length is even then insert after middle, middle moves right
        else{
            Node after = middle.getNext();
            Node newMid = new Node(item, after, middle);
            middle.setNext(newMid);
            if (after != null) after.setPrev(newMid);
            middle = newMid;
            if (middle.getNext() == null) back = middle;
            length++;
        }
    }

    /**
     * Returns the number of elements in this mdeque.
     *
     * @return the number of elements in this mdeque
     */
    public int size(){
        return length;
    }

    /**
     * Returns a string representation of this mdeque.
     * The string representation consists of a list of the collection's elements
     * in order of how they are returned by its iterator
     * enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of this mdeque
     */
    public String toString(){
        if(length == 0){
            return "[]";
        }
        return "[" + recursiveString(front) + "]";
    }

    /**
     * returns the values in the MDeque as a string separated by commas using recursion
     *
     * @param current the current node of the MDeque
     *
     * @return the values in the MDeque as a string separated by commas
     */
    private String recursiveString(Node current){
        //if current is null then return an empty string
        if(current == null){
            return "";
        }
        //if the node after current is null return the string value of current
        if(current.getNext() == null){
            return "" + current.getVal();
        }
        //if none of the above return the string value of current
        //concatenate it with the string returned by this funtion with the node after current as the argument
        else{
            return "" + current.getVal() + ", " + recursiveString(current.getNext());
        }
    }
}
