package org.salerno.datastructure;

import com.google.common.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A singly linked list queue that contains references to random nodes within the list
 *
 * @author alex.salerno@me.com
 * @since July-2022
 */
public class RandomLinkedList implements Iterable<String> {

    // data structure vars
    @VisibleForTesting
    Node head;
    @VisibleForTesting
    Node tail;

    // local cache
    private static final Lock LOCK = new ReentrantLock();
    private static final HashMap<String, Node> COPY_CACHE = new HashMap<>();
    private static int length = -1;
    private static Node currentNode = null;

    // constants
    /**
     * Maximum recursive copy depth to prevent {@code StackOverflowError}
     */
    private static final int MAX_COPY_DEPTH = 1000;

    /* ---------------------------------- Constructor Methods ---------------------------------- **/

    private RandomLinkedList(final Node node) {
        this.head = node;
    }

    /* ---------------------------------- Public Methods ---------------------------------- **/

    /**
     * @param tags A collection of strings that identify a node in the list
     * @return A singly linked list that contains references to random nodes within the list, may return {@code null}
     */
    public static RandomLinkedList asList(final String... tags) {
        final RandomLinkedList list = new RandomLinkedList(null);
        if(tags == null) return list;

        // create linked list
        Node head = null;
        Node current = null;
        // for each tag, create a node and link it
        for(String tag : tags) {
            if(tag == null) continue;
            if(head == null) {
                head = new Node(tag);
                current = head;
                list.tail = head;
            } else {
                current.next = new Node(tag);
                current = current.next;
                list.tail = current;
            }
        }

        if(head != null) {
            list.head = head;
            // randomly assign each node another node reference
            synchronized (LOCK) {
                for(Node node : head) {
                    node.reference = list.getRandomNode();
                }
                clearLengthCache();
            }
        }
        return list;
    }

    /**
     * @param list A singly linked list that contains references to random nodes within the list
     * @return A duplicate copy of the list with no dependency on the original
     */
    public static synchronized RandomLinkedList duplicateList(final RandomLinkedList list) {
        if(list == null || list.head == null) return null;

        // copy next
        currentNode = list.head;
        do {
            deepCopy(currentNode, 1);
        } while(currentNode.next != null);
        // copy reference
        for(Node node : list.head){
            final Node copy = COPY_CACHE.get(node.tag);
            copy.reference = COPY_CACHE.get(node.reference.tag);
        }
        // return results
        final Node result = COPY_CACHE.get(list.head.tag);
        clearDuplicateCache();
        return new RandomLinkedList(result);
    }

    @Override
    public String toString() {
        if(this.head == null) return "[]";
        StringBuilder result = new StringBuilder("[");
        for(Node node : this.head) {
            result.append(node).append(", ");
        }
        return result.substring(0,result.length()-2) + "]";
    }

    public void push(final String string) {
        final Node node = new Node(string);
        if(this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = this.tail.next;
        }
        synchronized (LOCK) {
            node.reference = getRandomNode();
            clearLengthCache();
        }
    }

    public String pop() {
        if(this.head == null) return "";
        final String tag = this.head.tag;
        this.head = this.head.next;
        return tag;
    }

    public String get() {
        if(this.head != null) return this.head.tag;
        return "";
    }

    public RandomLinkedList getNext() {
        if(this.head != null) return new RandomLinkedList(this.head.next);
        return null;
    }

    public RandomLinkedList getReference() {
        if(this.head != null) return new RandomLinkedList(this.head.reference);
        return null;
    }

    public int getLength() {
        int result;
        synchronized (LOCK) {
            this.calculateLength();
            result = length;
            clearLengthCache();
        }
        return result;
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
    private static Node deepCopy(final Node node, final int depth) {
        currentNode = node; // cache current location in list
        if(currentNode == null || depth > MAX_COPY_DEPTH) return null;
        // copy node
        final Node copy = new Node(currentNode.tag);
        COPY_CACHE.put(copy.tag, copy);
        // copy 'next' node
        if(currentNode.next == null) return copy;
        else copy.next = deepCopy(currentNode.next, depth+1);

        return copy;
    }

    /**
     * @return A random node from the list
     */
    private Node getRandomNode() {
        // get node total
        if(length == -1) { this.calculateLength(); }
        if(length == 0) return null;
        // select random node
        Node current = this.head;
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
        if(this.head == null) return;
        for(Node ignored : this.head) {
            length += 1;
        }
    }

    /**
     * Clears duplication cached values
     */
    private static void clearDuplicateCache() {
        COPY_CACHE.clear();
        currentNode = null;
    }

    private static void clearLengthCache() {
        length = -1;
    }

    /* ---------------------------------- Node Class ---------------------------------- **/

    @VisibleForTesting
    static class Node implements Iterable<Node> {

        // data structure vars
        private Node next;
        private final String tag;
        private Node reference;

        private Node(final String tag) {
            this.tag = tag;
        }

        @VisibleForTesting
        Node getNext() {
            return next;
        }

        @VisibleForTesting
        String getTag() {
            return tag;
        }

        @VisibleForTesting
        Node getReference() {
            return reference;
        }

        @Override
        public String toString() {
            return this.tag + "@" + hashCode() + "->" + this.reference.tag + "@" + this.reference.hashCode();
        }

        /* ---------------------------------- Node Iterator ---------------------------------- **/

        @Override
        public Iterator<Node> iterator() {
            return new Node.NodeIterator(this);
        }

        static class NodeIterator implements Iterator<Node> {

            private Node node;

            private NodeIterator(final Node node) {
                this.node = node;
            }

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Node next() {
                final Node result = this.node;
                this.node = this.node.next;
                return result;
            }

        }

    }

    /* ---------------------------------- List Iterator ---------------------------------- **/

    @Override
    public Iterator<String> iterator() {
        return new RandomLinkedListIterator(this);
    }

    static class RandomLinkedListIterator implements Iterator<String> {

        private final Node.NodeIterator iterator;

        private RandomLinkedListIterator(final RandomLinkedList list) {
            if(list.head != null) this.iterator = (Node.NodeIterator) list.head.iterator();
            else this.iterator = null;
        }

        @Override
        public boolean hasNext() {
            if(this.iterator == null) return false;
            return this.iterator.hasNext();
        }

        @Override
        public String next() {
            return this.iterator.next().tag;
        }

    }

}
