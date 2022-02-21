package de.c8121.packing.util;

import de.c8121.packing.Container;
import de.c8121.packing.ContainerState;
import de.c8121.packing.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of packaging.
 */
public class BasicContainerState implements ContainerState {

    private final Container container;

    private final List<Item> items = new ArrayList<>();
    private int currentWeight;

    /**
     *
     */
    public BasicContainerState(Container container) {
        this.container = container;
        this.currentWeight = 0;
    }

    /**
     *
     */
    @Override
    public Container container() {
        return this.container;
    }

    /**
     *
     */
    @Override
    public List<Item> items() {
        return List.copyOf(this.items);
    }

    /**
     *
     */
    public void addItem(final Item item) {
        this.items.add(item);
        this.currentWeight += item.weight();
    }

    /**
     *
     */
    @Override
    public int remainWeight() {
        return this.container.maxLoadWeight() - this.currentWeight;
    }

}
