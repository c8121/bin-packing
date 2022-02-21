package de.c8121.packing;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of packaging for one {@link Container}
 */
public interface ContainerState {

    /**
     *
     */
    Container container();

    /**
     * All items that have been packed into {@link #container()}
     */
    List<Item> items();


    /**
     * Remaining weight which the {@link #container()} can take
     */
    int remainWeight();

}
