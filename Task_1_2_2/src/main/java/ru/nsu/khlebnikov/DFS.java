package ru.nsu.khlebnikov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * My realization of Deep First Search.
 *
 * @param <T> type parameter
 */
public class DFS<T> implements Iterator<T> {
    private final int modCounter;
    private Stack<Tree<T>> stack = new Stack<Tree<T>>();

    /**
     * Realization of Iterator for Deep First Search that
     * uses stack to traverse the tree.
     * Traverses the tree according to {@link #next} and {@link #hasNext} methods.
     *
     * @param root root of the tree
     */
    public DFS(Tree<T> root) {
        stack.add(root);
        modCounter = root.getModCounter();
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        Tree<T> node = stack.pop();
        if (modCounter != node.getModCounter()) {
            throw new ConcurrentModificationException("You mustn't change tree while iterator is on");
        }
        for (Tree<T> n : node.getChildren()) {
            stack.push(n);
        }
        return node.getData();
    }
}
