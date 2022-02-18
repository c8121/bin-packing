package de.c8121.packing.packers;

import de.c8121.packing.*;

import java.util.List;

public class SingleContainerPacker implements Packer {

    private final ContainerState containerState;


    /**
     *
     */
    public SingleContainerPacker(Container container) {
        this.containerState = new ContainerState(container);
    }

    /**
     *
     */
    @Override
    public PackItemResult add(Item item) {

        if (item.weight() > this.containerState.remainWeight())
            return PackItemResult.FailedToHeavy;

        if (!Dimension.fitsIn(item, this.containerState.container()))
            return PackItemResult.FailedDoesNotFitIn;

        return PackItemResult.FailedReasonUnknown;
    }
}
