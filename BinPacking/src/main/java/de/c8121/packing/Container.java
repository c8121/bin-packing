package de.c8121.packing;

/**
 * Takes multiple {@link Item}s.
 */
public interface Container extends Box {

    /**
     *
     */
    String name();

    /**
     * The maximum weight this container can load.
     */
    int maxLoadWeight();
}
