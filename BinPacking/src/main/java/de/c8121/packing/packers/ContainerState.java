package de.c8121.packing.packers;

import de.c8121.packing.Container;

public class ContainerState {

    private final Container container;

    private int remainWeight;


    /**
     *
     */
    public ContainerState(Container container) {
        this.container = container;
        this.remainWeight = container.maxLoadWeight();
    }

    /**
     *
     */
    public Container container() {
        return this.container;
    }

    /**
     *
     */
    public int remainWeight() {
        return this.remainWeight;
    }
}
