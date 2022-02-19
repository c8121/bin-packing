package de.c8121.packing;

public class Item extends Box {

    private final int weight;

    /**
     *
     */
    public Item(final Dimension dimension, final int weight) {
        super(null, dimension);
        this.weight = weight;
    }

    public Item(final int xs, final int ys, final int zs, final int weight) {
        super(xs, ys, zs);
        this.weight = weight;
    }

    /**
     *
     */
    public int weight() {
        return this.weight;
    }
}
