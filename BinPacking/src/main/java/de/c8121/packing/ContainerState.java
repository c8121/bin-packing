package de.c8121.packing;

import java.util.List;

/**
 * Represents the current state of packaging for one {@link Container}
 */
public interface ContainerState {

    /**
     *
     */
    Container container();

    /**
     * First/Root-{@link Placement} where packaging start,
     * created by {@link Packer}-Implementation.
     */
    Placement rootPlacement();

    /**
     * All items that have been packed into {@link #container()}
     */
    List<Item> items();


    /**
     * Remaining weight which the {@link #container()} can take
     */
    int remainWeight();

    default int itemsVolume() {
        int volume = 0;
        for (var item : items()) {
            volume += item.volume();
        }

        return volume;
    }

    /**
     * Remaining (unused) volume
     */
    default int remainVolume() {

        int volume = container().volume();
        for (var item : items()) {
            volume -= item.volume();
        }

        return volume;
    }

    /**
     * Filling level as double 0...1 calculated from volume.
     */
    default double fillingLevel() {
        return (double) this.itemsVolume() / this.container().volume();
    }
}
