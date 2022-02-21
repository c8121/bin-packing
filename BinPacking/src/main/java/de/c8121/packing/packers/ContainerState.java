package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;

import java.util.ArrayList;
import java.util.List;

public class ContainerState {

    private final Container container;

    private final List<Item> items = new ArrayList<>();
    private int currentWeight;


    /**
     *
     */
    public ContainerState(Container container) {
        this.container = container;
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
    public int remainWeight() {
        return this.container.maxLoadWeight() - this.currentWeight;
    }

}
