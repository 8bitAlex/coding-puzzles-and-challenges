package org.salerno.datastructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomLinkedListTest {

    private static final String[] TAGS = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};
    private static final String PLANET_ONE = "Hoth";

    @Test
    public void shouldCreateLinkedList() {
        RandomLinkedList list = RandomLinkedList.asList(TAGS);
        // verify list is in order
        for(String tag: TAGS) {
            assertNotNull(list);
            assertSame(tag, list.peek());
            assertNotNull(list.getReference());
            list = list.getNext();
        }
        // verify list is now empty
        assertSame("", list.peek());
    }

    @Test
    public void shouldPushAndPop() {
        final String planetTwo = "Tattoine";
        // verify push and pop
        final RandomLinkedList listOne = RandomLinkedList.asList(TAGS);
        final String tag = listOne.pop();
        assertSame(TAGS[0], tag);
        final int length = listOne.size();
        listOne.push(PLANET_ONE);
        assertSame(length+1, listOne.size());
        // verify order is maintained
        final RandomLinkedList listTwo = RandomLinkedList.asList(PLANET_ONE);
        listTwo.push(planetTwo);
        assertSame(PLANET_ONE, listTwo.pop());
        assertSame(planetTwo, listTwo.pop());
        assertSame("", listTwo.pop());
    }

    @Test
    public void shouldDuplicateList() {
        final RandomLinkedList list = RandomLinkedList.asList(TAGS);
        final RandomLinkedList copy = RandomLinkedList.duplicateList(list);
        // verify nodes are copies and have no shared dependencies
        final RandomLinkedList.Node nodes = list.head;
        RandomLinkedList.Node copyNode = copy.head;
        for(RandomLinkedList.Node node : nodes) {
            assertNotNull(copyNode);
            assertNotNull(copyNode.getReference());
            assertSame(node.getTag(), copyNode.getTag());
            assertNotSame(node, copyNode);
            copyNode = copyNode.getNext();
        }
        assertNull(copyNode);
    }

    @Test
    public void shouldDuplicateLargeListEfficiently() {
        final int maxListSize = 250000;
        // generate large number of tags
        ArrayList<String> tags = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < maxListSize; i++) {
            tags.add(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        }
        // duplicate
        RandomLinkedList list = RandomLinkedList.asList(tags.toArray(new String[maxListSize]));
        RandomLinkedList copy = RandomLinkedList.duplicateList(list);
        // verify first 100 entries
        for(int i = 0; i < 100; i++) {
            assertNotNull(list);
            assertNotNull(copy);
            assertSame(list.peek(), copy.peek());
            assertNotSame(list, copy);
            list = list.getNext();
            copy = copy.getNext();
        }
    }

    @Test
    public void shouldHandleBadData() {
        // null
        RandomLinkedList list = RandomLinkedList.asList((String) null);
        assertSame("", list.peek());
        list.push(PLANET_ONE);
        list.push(null);
        assertSame(PLANET_ONE, list.pop());
        // empty array
        list = RandomLinkedList.asList(new String[3]);
        assertSame("", list.peek());
        // duplicate tags (Not awful but could cause issues)
        list = RandomLinkedList.asList(PLANET_ONE, PLANET_ONE, PLANET_ONE);
        assertSame(PLANET_ONE, list.peek());
        assertSame(3, list.size());
    }

    @Test
    public void shouldBeThreadSafe() {
        final Thread threadOne = new Thread(RandomLinkedListTest::concurrentTest);
        final Thread threadTwo = new Thread(RandomLinkedListTest::concurrentTest);
        threadOne.start();
        threadTwo.start();
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    private static void concurrentTest() {
        // run 500 times with no conflicts
        try {
            for(int i = 0; i<500; i++) {
                final RandomLinkedList list = RandomLinkedList.asList(TAGS);
                final RandomLinkedList copy = RandomLinkedList.duplicateList(list);
                final int listLength = list.size();
                final int copyLength = copy.size();
                assertSame(listLength, copyLength);
                list.push(PLANET_ONE);
                assertNotNull(list.tail.getReference());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
