package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Packer;

public abstract class AbstractPacker implements Packer {

    protected final Container container;

    /**
     *
     */
    protected AbstractPacker(Container container) {
        this.container = container;
    }

    /**
     *
     */
    @Override
    public Container container() {
        return this.container;
    }
}
