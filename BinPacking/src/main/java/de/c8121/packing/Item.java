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

    public Item(final int w, final int h, final int d, final int weight) {
        super(w, h, d);
        this.weight = weight;
    }

    /**
     *
     */
    public int weight() {
        return this.weight;
    }
}
