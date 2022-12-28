package ru.nsu.khlebnikov;

public record Edge<T>(int weight, Vertex<T> from, Vertex<T> to) {}
