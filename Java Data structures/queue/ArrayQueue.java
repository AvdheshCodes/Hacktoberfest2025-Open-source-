/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

/**
 * An implementation of the Queue interface using a dynamic array.
 * Note: This implementation's remove() operation has a time complexity of O(n)
 * because it shifts all elements after the first one. A circular array
 * would be a more efficient approach for an array-based queue.
 *
 * @author Zohaib Hassan Soomro
 */
public class ArrayQueue implements Queue { // Assumes a Queue interface is defined elsewhere

    // --- Instance Variables ---

    /**
     * The number of elements currently stored in the queue.
     */
    private int size;

    /**
     * The array used to store the elements of the queue.
     */
    private Object array[];

    // --- Constructor ---

    /**
     * Constructs an empty queue with a specified initial capacity.
     *
     * @param capacity The initial size of the internal array.
     */
    public ArrayQueue(int capacity) {
        array = new Object[capacity];
        // The 'size' is implicitly initialized to 0.
    }

    // --- Queue Interface Methods ---

    /**
     * Returns the first element in the queue without removing it.
     *
     * @return The first object in the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    @Override
    public Object first() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }
        // The first element in this implementation is always at index 0.
        return array[0];
    }

    /**
     * Removes and returns the first element from the queue.
     * All subsequent elements are shifted to the left.
     *
     * @return The object that was removed.
     * @throws IllegalStateException if the queue is empty.
     */
    @Override
    public Object remove() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty!");
        }
        // Store the first element to return it later.
        Object obj = array[0];

        // Shift all elements from index 1 to the left by one position.
        // This is an inefficient O(n) operation.
        System.arraycopy(array, 1, array, 0, size - 1);

        // Decrement the size and set the now-unused last slot to null
        // to help the garbage collector.
        array[--size] = null;

        return obj;
    }

    /**
     * Adds a new element to the end of the queue.
     * If the internal array is full, it will be resized.
     *
     * @param obj The object to be added to the queue.
     */
    @Override
    public void add(Object obj) {
        // Check if the array is full.
        if (size == this.array.length) {
            // If full, double the array's capacity.
            resizeArray();
        }
        // Add the new object at the end of the queue and increment the size.
        array[size++] = obj;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return The current size of the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // --- Helper Method ---

    /**
     * A private helper method to increase the capacity of the array.
     * It creates a new array with double the capacity and copies the
     * elements from the old array to the new one.
     */
    public void resizeArray() {
        // Temporarily hold a reference to the old array.
        Object[] oldArray = this.array;
        // Create a new array with double the capacity of the current size.
        this.array = new Object[2 * size];
        // Copy all elements from the old array to the new, larger array.
        System.arraycopy(oldArray, 0, this.array, 0, oldArray.length);
    }

    // --- Main Method for Testing ---

    /**
     * A simple main method to demonstrate the functionality of the ArrayQueue.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new queue with an initial capacity of 2.
        ArrayQueue queue = new ArrayQueue(2);

        // Add two elements. The array is now full.
        queue.add(5);
        queue.add(50);

        // Add a third element. This will trigger the resizeArray() method.
        queue.add("Hello!");

        // Remove and print the first element (FIFO - First In, First Out).
        System.out.println(queue.remove()); // Expected output: 5

        // Remove and print the next element.
        System.out.println(queue.remove()); // Expected output: 50

        // View the new first element without removing it.
        System.out.println(queue.first());  // Expected output: Hello!
    }
}
