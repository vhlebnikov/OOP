package ru.nsu.khlebnikov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TreeTest {

    @Test
    public void treeEqualsTest() throws Exception {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child_1 = root.addChild(2);
        Tree<Integer> child_2 = root.addChild(3);
        Tree<Integer> child_3 = root.addChild(3);
        Tree<Integer> child_1_1 = child_1.addChild(4);
        Tree<Integer> child_1_2 = child_1.addChild(5);
        Tree<Integer> child_1_3 = child_1.addChild(6);
        Tree<Integer> child_2_1 = child_2.addChild(7);
        Tree<Integer> child_2_2 = child_2.addChild(8);
        Tree<Integer> child_1_2_1 = child_1_2.addChild(9);
        Tree<Integer> child_2_1_1 = child_2_1.addChild(10);
        Tree<Integer> child_2_1_2 = child_2_1.addChild(11);
        Tree<Integer> root1 = new Tree<>(1);
        Tree<Integer> child_1r1 = root1.addChild(2);
        Tree<Integer> child_2r1 = root1.addChild(3);
        Tree<Integer> child_3r1 = root1.addChild(3);
        Tree<Integer> child_1_1r1 = child_1r1.addChild(4);
        Tree<Integer> child_1_2r1 = child_1r1.addChild(5);
        Tree<Integer> child_1_3r1 = child_1r1.addChild(6);
        Tree<Integer> child_2_1r1 = child_2r1.addChild(7);
        Tree<Integer> child_2_2r1 = child_2r1.addChild(8);
        Tree<Integer> child_1_2_1r1 = child_1_2r1.addChild(9);
        Tree<Integer> child_2_1_1r1 = child_2_1r1.addChild(10);
        Tree<Integer> child_2_1_2r1 = child_2_1r1.addChild(11);
        Assertions.assertTrue(root.treeEquals(root1));
    }

    @Test
    public void removeTest() throws Exception {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> c_1 = root.addChild(2);
        Tree<Integer> c_2 = root.addChild(3);
        Tree<Integer> c_1_1 = c_1.addChild(4);
        Tree<Integer> c_1_2 = c_1.addChild(5);
        Tree<Integer> c_1_3 = c_1.addChild(6);
        Tree<Integer> c_1_2_1 = c_1_2.addChild(7);
        Tree<Integer> c_1_2_2 = c_1_2.addChild(8);
        root.setTypeOfSearch(2);
        c_1_2.removeNode();
        Iterator<Integer> iterator = root.iterator();
        ArrayList<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        Integer[] e = new Integer[]{1, 2, 3, 4, 7, 8, 6};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(e));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void addAndRemoveTest() throws Exception {
        Tree<String> root = new Tree<>("Root");
        Tree<String> c_1 = root.addChild("Left child");
        Tree<String> c_2 = root.addChild("Right child");
        Tree<String> c_1_1 = c_1.addChild("First child of c1");
        Tree<String> c_1_1_1 = c_1_1.addChild("First child of c11");
        Tree<String> c_2_1 = c_2.addChild("First child of c2");
        Tree<String> c_2_2 = c_2.addChild("Second child of c2");
        c_1.removeNode();
        c_2.removeNode();
        c_1_1.removeNode();
        c_1_1_1.removeNode();
        c_2_1.removeNode();
        c_2_2.removeNode();
        Tree<String> rootExpected = new Tree<>("Root");
        Assertions.assertEquals(root, rootExpected);
    }

    @Test
    public void testBFS() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child_1 = root.addChild(2);
        Tree<Integer> child_2 = root.addChild(3);
        Tree<Integer> child_1_1 = child_1.addChild(4);
        Tree<Integer> child_1_2 = child_1.addChild(5);
        Tree<Integer> child_1_3 = child_1.addChild(6);
        Tree<Integer> child_2_1 = child_2.addChild(7);
        Tree<Integer> child_2_2 = child_2.addChild(8);
        Tree<Integer> child_1_2_1 = child_1_2.addChild(9);
        Tree<Integer> child_2_1_1 = child_2_1.addChild(10);
        Tree<Integer> child_2_1_2 = child_2_1.addChild(11);
        root.setTypeOfSearch(2);
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
    public void testDFS() {
        Tree<Integer> root = new Tree<>(1);
        Tree<Integer> child_1 = root.addChild(2);
        Tree<Integer> child_2 = root.addChild(3);
        Tree<Integer> child_1_1 = child_1.addChild(4);
        Tree<Integer> child_1_2 = child_1.addChild(5);
        Tree<Integer> child_1_3 = child_1.addChild(6);
        Tree<Integer> child_2_1 = child_2.addChild(7);
        Tree<Integer> child_2_2 = child_2.addChild(8);
        Tree<Integer> child_1_2_1 = child_1_2.addChild(9);
        Tree<Integer> child_2_1_1 = child_2_1.addChild(10);
        Tree<Integer> child_2_1_2 = child_2_1.addChild(11);
        Iterator<Integer> iterator = root.iterator();
        ArrayList<Integer> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        Integer[] e = new Integer[]{1, 3, 8, 7, 11, 10, 2, 6, 5, 9, 4};
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(e));
        Assertions.assertEquals(actual, expected);
    }
}