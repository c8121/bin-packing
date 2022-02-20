package de.c8121.packing.packers;

import de.c8121.packing.Container;

public class ContainerState {

    private final Container container;

    /**
     * Root placement
     */
    private final Placement placement;

    private int currentWeight;


    /**
     *
     */
    public ContainerState(Container container) {
        this.container = container;
        this.placement = new Placement(container);
        this.currentWeight = 0;
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
        return this.container.maxLoadWeight() - this.currentWeight;
    }

    /**
     * Get the first placement (root placement) to start packaging at.
     */
    public Placement placement() {
        return this.placement;
    }
}
