package ru.nsu.khlebnikov;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.khlebnikov.Tree.IteratorType.BFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for my tree realization.
 */
public class TreeTest {

    @Test
    public void treeEqualsTest() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child1 = root.addChild(2);
        Tree<Integer> child2 = root.addChild(3);
        Tree<Integer> child3 = root.addChild(3);
        Tree<Integer> child11 = child1.addChild(4);
        Tree<Integer> child12 = child1.addChild(5);
        Tree<Integer> child13 = child1.addChild(6);
        Tree<Integer> child21 = child2.addChild(7);
        Tree<Integer> child22 = child2.addChild(8);
        Tree<Integer> child121 = child12.addChild(9);
        Tree<Integer> child211 = child21.addChild(10);
        Tree<Integer> child212 = child21.addChild(11);
        Tree<Integer> root1 = new Tree<>(1);
        Tree<Integer> child1r1 = root1.addChild(2);
        Tree<Integer> child2r1 = root1.addChild(3);
        Tree<Integer> child3r1 = root1.addChild(3);
        Tree<Integer> child11r1 = child1r1.addChild(4);
        Tree<Integer> child12r1 = child1r1.addChild(5);
        Tree<Integer> child13r1 = child1r1.addChild(6);
        Tree<Integer> child21r1 = child2r1.addChild(7);
        Tree<Integer> child22r1 = child2r1.addChild(8);
        Tree<Integer> child121r1 = child12r1.addChild(9);
        Tree<Integer> child211r1 = child21r1.addChild(10);
        Tree<Integer> child212r1 = child21r1.addChild(11);
        Assertions.assertEquals(root, root1);
    }

    @Test
    public void removeTest() throws Exception {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> c1 = root.addChild(2);
        Tree<Integer> c2 = root.addChild(3);
        Tree<Integer> c11 = c1.addChild(4);
        Tree<Integer> c12 = c1.addChild(5);
        Tree<Integer> c13 = c1.addChild(6);
        Tree<Integer> c121 = c12.addChild(7);
        Tree<Integer> c122 = c12.addChild(8);
        c12.removeNode();
        c13.removeNode();
        root.setTypeOfSearch(BFS);
        Iterator<Integer> iterator = root.iterator();
        ArrayList<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        Integer[] e = new Integer[]{1, 2, 3, 4, 7, 8};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(e));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testBreathFirstSearch() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child1 = root.addChild(2);
        Tree<Integer> child2 = root.addChild(3);
        Tree<Integer> child11 = child1.addChild(4);
        Tree<Integer> child12 = child1.addChild(5);
        Tree<Integer> child13 = child1.addChild(6);
        Tree<Integer> child21 = child2.addChild(7);
        Tree<Integer> child22 = child2.addChild(8);
        Tree<Integer> child121 = child12.addChild(9);
        Tree<Integer> child211 = child21.addChild(10);
        Tree<Integer> child212 = child21.addChild(11);
        root.setTypeOfSearch(BFS);
        Iterator<Integer> iterator = root.iterator();
        ArrayList<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        Integer[] e = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(e));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDeepFirstSearch() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child1 = root.addChild(2);
        Tree<Integer> child2 = root.addChild(3);
        Tree<Integer> child11 = child1.addChild(4);
        Tree<Integer> child12 = child1.addChild(5);
        Tree<Integer> child13 = child1.addChild(6);
        Tree<Integer> child21 = child2.addChild(7);
        Tree<Integer> child22 = child2.addChild(8);
        Tree<Integer> child121 = child12.addChild(9);
        Tree<Integer> child211 = child21.addChild(10);
        Tree<Integer> child212 = child21.addChild(11);
        Iterator<Integer> iterator = root.iterator();
        ArrayList<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        Integer[] e = new Integer[]{1, 3, 8, 7, 11, 10, 2, 6, 5, 9, 4};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(e));
        Assertions.assertEquals(actual, expected);
    }

    /**
     * Testing ConcurrentModificationException for Deep First Search and Breath First Search.
     */
    @Test
    public void concurrentModificationExceptionTest() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child1 = root.addChild(2);
        Tree<Integer> child2 = root.addChild(3);
        Iterator<Integer> iterator = root.iterator();
        iterator.next();
        Tree<Integer> child3 = root.addChild(4);
        Throwable exception =
                assertThrows(ConcurrentModificationException.class, iterator::next);
        Assertions.assertEquals(exception.getMessage(), "You mustn't change"
                + "tree while iterator is on");

        Tree<Integer> root1 = new Tree<>(1);
        Tree<Integer> child11 = root1.addChild(2);
        Tree<Integer> child21 = root1.addChild(3);
        root1.setTypeOfSearch(BFS);
        Iterator<Integer> iterator1 = root1.iterator();
        iterator1.next();
        Tree<Integer> child31 = root1.addChild(4);
        Throwable exception1 =
                assertThrows(ConcurrentModificationException.class, iterator1::next);
        Assertions.assertEquals(exception1.getMessage(), "You mustn't change"
                + "tree while iterator is on");
    }

    @Test
    public void otherExceptionsTest() {
        Tree<Integer> root2 = new Tree<>(1);
        Tree<Integer> child12 = root2.addChild(2);
        Throwable exception1 = assertThrows(Exception.class, root2::removeNode);
        Assertions.assertEquals(exception1.getMessage(), "Can't remove root");
    }

    @Test
    public void notEqualsTest() {
        Tree<String> root = new Tree<>("1");
        Tree<String> child1 = root.addChild("2");
        Tree<String> child2 = root.addChild("3");
        Tree<String> root1 = new Tree<>("1");
        Tree<String> child11 = root1.addChild("2");
        Tree<String> child21 = root1.addChild("4");
        Assertions.assertNotEquals(root, root1);
    }
}