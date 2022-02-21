package de.c8121.packing.util;

import de.c8121.packing.Item;

public class BasicItem extends BasicBox implements Item {

    private final int weight;

    /**
     *
     */
    public BasicItem(final int xs, final int ys, final int zs, final int weight) {
        super(xs, ys, zs);
        this.weight = weight;
    }

    /**
     * Copy constructor
     */
    public BasicItem(final Item item) {
        super(item);
        this.weight = item.weight();
    }

    /**
     *
     */
    public int weight() {
        return this.weight;
    }
}
