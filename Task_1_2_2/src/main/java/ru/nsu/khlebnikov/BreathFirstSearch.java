package ru.nsu.khlebnikov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * My Breath First Search realization.
 *
 * @param <T> type parameter
 */
public class BreathFirstSearch<T> implements Iterator<T> {

    private final int modCounter;
    private Queue<Tree<T>> queue = new LinkedList<Tree<T>>();

    /**
     * Realization of Iterator for Breath First Search that
     * uses queue to traverse the tree.
     * Traverses the tree according to {@link #next} and {@link #hasNext} methods.
     *
     * @param root root of the tree
     */
    public BreathFirstSearch(Tree<T> root) {
        queue.add(root);
        modCounter = root.getModCounter();
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        Tree<T> node = queue.poll();
        assert node != null;
        if (modCounter != node.getModCounter()) {
            throw new ConcurrentModificationException("You mustn't change" +
                    "tree while iterator is on");
        }
        queue.addAll(node.getChildren());
        return node.getData();
    }
}
