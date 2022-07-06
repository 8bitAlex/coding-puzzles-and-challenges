package org.salerno.datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A singly linked list that contains references to random nodes within the list
 *
 * @author alex.salerno@me.com
 * @since July-2022
 */
public class RandomLinkedList implements Iterable<RandomLinkedList> {

    // data structure vars
    private RandomLinkedList next;
    private final String tag;
    private RandomLinkedList reference;

    // local cache
    private static final Lock lock = new ReentrantLock();
    private static final HashMap<String, RandomLinkedList> copyCache = new HashMap<>();
    private static int length = -1;
    private static RandomLinkedList currentNode = null;

    // constants
    /**
     * Maximum recursive copy depth to prevent {@code StackOverflowError}
     */
    private static final int MAX_COPY_DEPTH = 1000;

    /* ---------------------------------- Constructor Methods ---------------------------------- **/

    private RandomLinkedList(final String tag) {
        this.tag = tag;
    }

    /* ---------------------------------- Public Methods ---------------------------------- **/

    /**
     * @param tags A collection of strings that identify a node in the list
     * @return A singly linked list that contains references to random nodes within the list
     */
    public static RandomLinkedList asList(final String... tags) {
        if(tags == null) return null;

        RandomLinkedList head = null;
        RandomLinkedList current = null;
        // create linked list
        for(String tag : tags) {
            if(head == null) {
                head = new RandomLinkedList(tag);
                current = head;
            } else {
                current.next = new RandomLinkedList(tag);
                current = current.next;
            }
        }
        // randomly assign each node another node reference
        synchronized (lock) {
            for(RandomLinkedList node : head) {
                node.reference = head.getRandomNode();
            }
        }
        clearCache();
        return head;
    }

    /**
     * @param list A singly linked list that contains references to random nodes within the list
     * @return A duplicate copy of the list with no dependency on the original
     */
    public static synchronized RandomLinkedList duplicateList(final RandomLinkedList list) {
        if(list == null) return null;

        // copy next
        currentNode = list;
        do {
            deepCopy(currentNode, 1);
        } while(currentNode.next != null);
        // copy reference
        for(RandomLinkedList node : list){
            final RandomLinkedList copy = copyCache.get(node.tag);
            copy.reference = copyCache.get(node.reference.tag);
        }
        // return results
        final RandomLinkedList result = copyCache.get(list.tag);
        clearCache();
        return result;
    }

    @Override
    public String toString() {
        String result = "[";
        for(RandomLinkedList node : this) {
            result += node.tag + "@" + node.hashCode() + "->" + node.reference.tag + "@" + node.reference.hashCode() + ", ";
        }
        return result.substring(0,result.length()-2) + "]";
    }

    public RandomLinkedList getNext() {
        return this.next;
    }

    public String getTag() {
        return this.tag;
    }

    public RandomLinkedList getReference() {
        return this.reference;
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    /**
     * A recursive function that creates a deep copy of a given {@code Node} with no dependencies on the original.
     * <br><br>Java does not support tail-recursion so there is a maximum depth of {@code MAX_COPY_DEPTH}.
     * If the maximum depth is reached, current location is stored in cache {@code currentNode} and the recursion
     * stack frames are reset. The copy can be continued by calling {@code deepCopy} again with the cached {@code currentNode}.
     *
     * @param node The node to copy
     * @param depth The current recursive depth, compared to {@code MAX_COPY_DEPTH}
     * @return A copy of the node with no dependencies on the original
     */
    private static RandomLinkedList deepCopy(final RandomLinkedList node, final int depth) {
        currentNode = node; // cache current location in list
        if(currentNode == null || depth > MAX_COPY_DEPTH) return null;
        // copy node
        final RandomLinkedList copy = new RandomLinkedList(currentNode.tag);
        copyCache.put(copy.tag, copy);
        // copy 'next' node
        if(currentNode.next == null) return copy;
        else copy.next = deepCopy(currentNode.next, depth+1);

        return copy;
    }

    /**
     * @return A random node from the list
     */
    private RandomLinkedList getRandomNode() {
        // get node total
        if(length == -1) { this.calculateLength(); }
        // select random node
        RandomLinkedList current = this;
        Random rand = new Random();
        for(int i = rand.nextInt(length); i>0; i--) {
            current = current.next;
        }
        return current;
    }

    /**
     * Calculates the length of the list and stores it in a cache
     */
    private void calculateLength() {
        length = 0;
        for(RandomLinkedList ignored : this) {
            length += 1;
        }
    }

    /**
     * Clears all cached values
     */
    private static void clearCache() {
        copyCache.clear();
        length = -1;
        currentNode = null;
    }

    /* ---------------------------------- Iterator Methods ---------------------------------- **/

    @Override
    public Iterator<RandomLinkedList> iterator() {
        return new RandomLinkedListIterator(this);
    }

    static class RandomLinkedListIterator implements Iterator<RandomLinkedList> {

        private RandomLinkedList node;

        private RandomLinkedListIterator(RandomLinkedList node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public RandomLinkedList next() {
            final RandomLinkedList result = this.node;
            this.node = this.node.next;
            return result;
        }

    }

}
