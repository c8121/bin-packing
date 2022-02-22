package de.c8121.packing.util;

import de.c8121.packing.Box;
import de.c8121.packing.Container;

public class BasicContainer extends BasicBox implements Box, Container {

    private final int maxLoadWeight;

    /**
     *
     */
    public BasicContainer(final int w, final int h, final int d, final int maxLoadWeight) {
        super(w, h, d);
        this.maxLoadWeight = maxLoadWeight;
    }

    /**
     * Copy constructor
     */
    public BasicContainer(final Container container) {
        super(container);
        this.maxLoadWeight = container.maxLoadWeight();
    }

    /**
     *
     */
    public int maxLoadWeight() {
        return this.maxLoadWeight;
    }
}
