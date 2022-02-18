package de.c8121.packing;

public class Container extends Box {

    private final int maxLoadWeight;

    /**
     *
     */
    public Container(final Dimension dimension, final int maxLoadWeight) {
        super(null, dimension);
        this.maxLoadWeight = maxLoadWeight;
    }

    public Container(final int w, final int h, final int d, final int maxLoadWeight) {
        super(w, h, d);
        this.maxLoadWeight = maxLoadWeight;
    }

    /**
     *
     */
    public int maxLoadWeight() {
        return this.maxLoadWeight;
    }
}
