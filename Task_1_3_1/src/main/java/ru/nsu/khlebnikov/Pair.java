package ru.nsu.khlebnikov;

import java.util.Objects;

/**
 * Pair of elements.
 *
 * @param <T> - type of pair elements
 */
public class Pair<T> {
    private final T firstElement;
    private final T secondElement;


    public Pair(T first, T second) {
        this.firstElement = first;
        this.secondElement = second;
    }

    /**
     * An attractive way to create a pair.
     *
     * @param firstElement - first element of pair
     * @param secondElement - second element of pair
     * @param <T> - type of pair elements
     * @return - actually the pair
     */
    public static <T> Pair<T> create(T firstElement, T secondElement) {
        return new <T>Pair<T>(firstElement, secondElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?> pair = (Pair<?>) o;
        return firstElement.equals(pair.firstElement) && secondElement.equals(pair.secondElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstElement, secondElement);
    }
}
