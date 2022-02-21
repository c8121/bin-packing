package de.c8121.packing;

/**
 * Represents one item to be packed into a {@link Container}.
 */
public interface Item extends Box {

    /**
     *
     */
    int weight();
}
