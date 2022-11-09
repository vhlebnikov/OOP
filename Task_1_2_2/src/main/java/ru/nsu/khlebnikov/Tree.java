package ru.nsu.khlebnikov;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

/**
 * My realization of tree.
 *
 * @param <T> type parameter
 */
public class Tree<T> implements Iterable<T> {
    private T data;
    private Tree<T> parent;
    private List<Tree<T>> children;
    private int modCounter;

    /**
     * Type of iterator in the tree.
     */
    public enum IteratorType {
        DFS,
        BFS
    }
    
    private IteratorType typeOfSearch;

    /**
     * Initial constructor.
     *
     * @param data data that contains in the node
     */
    public Tree(T data) {
        this.data = data;
        children = new ArrayList<Tree<T>>();
        parent = null;
        modCounter = 0;
        typeOfSearch = IteratorType.DFS;
    }

    /**
     * Data getter.
     *
     * @return data of the node
     */
    public T getData() {
        return data;
    }

    /**
     * Parent getter.
     *
     * @return parent of the node
     */
    public Tree<T> getParent() {
        return parent;
    }

    /**
     * Shows how many times tree has been modified.
     *
     * @return modifications counter
     */
    public int getModCounter() {
        Tree<T> root = this.getRoot();
        return root.modCounter;
    }

    /**
     * Children list getter.
     *
     * @return list of node's children
     */
    public List<Tree<T>> getChildren() {
        return children;
    }

    /**
     * Data setter.
     *
     * @param data data to write
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Sets type of search.
     * <ul>
     *     <li> 1 for Deep First Search;
     *     <li> 2 for Breath First Search. (actually, anything different from 1).
     * </ul>
     *
     * @param typeOfSearch number that chooses type of search
     */


    public void setTypeOfSearch(IteratorType typeOfSearch) {
        this.typeOfSearch = typeOfSearch;
    }

    /**
     * Returns root of the tree.
     *
     * @return root of the tree
     */
    public Tree<T> getRoot() {
        Tree<T> root = this;
        while (root.parent != null) {
            root = root.getParent();
        }
        return root;
    }

    /**
     * Creates new node and adds it to current node.
     *
     * @param data data for new node
     * @return new node
     */
    public Tree<T> addChild(T data) {
        Tree<T> child = new Tree<T>(data);
        child.parent = this;
        this.children.add(child);
        this.modCounterIncrementation();
        return child;
    }

    /**
     * Removes node from the tree.
     *
     * @throws Exception You can't delete root node
     */
    public void removeNode() throws Exception {
        if (this.parent == null) {
            throw new Exception("Can't remove root");
        }
        if (this.children.size() != 0) {
            int index = this.getParent().children.indexOf(this);
            int size = this.getParent().children.size();
            List<Tree<T>> subList =
                    new ArrayList<>(this.getParent().children.subList(index + 1, size));
            this.getParent().children.addAll(this.children);
            size = this.getParent().children.size();
            this.getParent().children.subList(index, size - this.children.size()).clear();
            this.getParent().children.addAll(subList);
            for (Tree<T> c : this.children) {
                c.parent = this.parent;
            }
        } else {
            this.getParent().children.remove(this);
        }
        this.modCounterIncrementation();
    }

    /**
     * Increments count of tree's modifications (such as add nodes to tree or remove them).
     */
    private void modCounterIncrementation() {
        Tree<T> root = this.getRoot();
        root.modCounter++;
    }

    @Override
    public Iterator<T> iterator() {
        if (typeOfSearch == IteratorType.DFS) {
            return new DeepFirstSearchIterator<>(this);
        } else {
            return new BreathFirstSearchIterator<>(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree<?> tree = (Tree<?>) o;
        return data.equals(tree.data) && children.equals(tree.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, parent, children);
    }
}
