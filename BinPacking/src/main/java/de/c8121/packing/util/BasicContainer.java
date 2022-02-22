package de.c8121.packing.util;

import de.c8121.packing.Box;
import de.c8121.packing.Container;

public class BasicContainer extends BasicBox implements Box, Container {

    private String name = null;
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
        this.name = container.name();
        this.maxLoadWeight = container.maxLoadWeight();
    }

    /**
     *
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     *
     */
    public void name(final String name) {
        this.name = name;
    }

    /**
     *
     */
    public int maxLoadWeight() {
        return this.maxLoadWeight;
    }
}
