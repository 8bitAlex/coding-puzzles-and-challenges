package org.salerno.datastructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomLinkedListTest {

    private final String[] tags = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Test
    public void createLinkedList() {
        RandomLinkedList node = RandomLinkedList.asList(tags);
        // verify list is in order
        for(String tag: tags) {
            assertNotNull(node);
            assertSame(node.getTag(), tag);
            assertNotNull(node.getReference());
            node = node.getNext();
        }
        assertNull(node);
    }

    @Test
    public void duplicateList() {
        final RandomLinkedList list = RandomLinkedList.asList(tags);
        RandomLinkedList copy = RandomLinkedList.duplicateList(list);
        // verify lists are copies and have no shared dependencies
        for(RandomLinkedList node : list) {
            assertNotNull(copy);
            assertNotNull(copy.getReference());
            assertSame(node.getTag(), copy.getTag());
            assertNotSame(node, copy);
            copy = copy.getNext();
        }
        assertNull(copy);
    }

    @Test
    public void duplicateLargeList() {
        final int maxListSize = 250000;
        ArrayList<String> tags = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < maxListSize; i++) {
            tags.add(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        }
        RandomLinkedList list = RandomLinkedList.asList(tags.toArray(new String[maxListSize]));
        RandomLinkedList copy = RandomLinkedList.duplicateList(list);
        // verify first 100 entries
        for(int i = 0; i < 100; i++) {
            assertNotNull(list);
            assertNotNull(copy);
            assertSame(list.getTag(), copy.getTag());
            assertNotSame(list, copy);
            list = list.getNext();
            copy = copy.getNext();
        }
    }

}
