package de.c8121.packing;

/**
 * Takes multiple {@link Item}s.
 */
public interface Container extends Box {

    /**
     * The maximum weight this container can load.
     */
    int maxLoadWeight();
}
