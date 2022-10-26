package ru.nsu.khlebnikov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * My realization of tree.
 *
 * @param <T> type parameter
 */
public class Tree<T> extends ArrayList<T> implements Iterable<T> {
    private T data;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;
    private int modCounter;
    private int typeOfSearch;

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
        typeOfSearch = 1;
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
    public ArrayList<Tree<T>> getChildren() {
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


    public void setTypeOfSearch(int typeOfSearch) {
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
        child.getParent().children.add(child);
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
            ArrayList<Tree<T>> subList =
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

    /**
     * Checks if two trees are equal or not.
     *
     * @param tree tree to compare
     * @return boolean value: true - if equals, otherwise - false
     * @throws Exception for these method types of search must be equivalent
     */
    public boolean treeEquals(Tree<T> tree) throws Exception {
        Tree<T> root = this.getRoot();
        Tree<T> root1 = tree.getRoot();
        boolean hasnext;
        boolean hasnext1;
        if (root.typeOfSearch != root1.typeOfSearch) {
            throw new Exception("Types of search must be equivalent");
        }
        Iterator<T> iterator = root.iterator();
        Iterator<T> iterator1 = root1.iterator();
        hasnext = iterator.hasNext();
        hasnext1 = iterator1.hasNext();
        while (hasnext && hasnext1) {
            if (!iterator.next().equals(iterator1.next())) {
                return false;
            }
            hasnext = iterator.hasNext();
            hasnext1 = iterator1.hasNext();
        }
        return !hasnext && !hasnext1;
    }

    @Override
    public Iterator<T> iterator() {
        if (typeOfSearch == 1) {
            return new DeepFirstSearch<>(this);
        }
        return new BreathFirstSearch<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tree<?> tree = (Tree<?>) o;
        if (parent == null || tree.parent == null) {
            if (!data.equals(tree.data) || parent != tree.parent
                    || children.size() != tree.children.size()) {
                return false;
            }
        }
        else if (!data.equals(tree.data) || !parent.data.equals(tree.parent.data)
                || children.size() != tree.children.size()){
            return false;
        }
        for (Tree<T> c : children) {
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).getData() != tree.children.get(i).getData()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, parent, children);
    }
}
