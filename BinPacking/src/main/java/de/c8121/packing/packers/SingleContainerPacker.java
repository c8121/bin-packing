package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Dimension;
import de.c8121.packing.Item;
import de.c8121.packing.Packer;

public class SingleContainerPacker implements Packer {

    private final ContainerState containerState;


    /**
     *
     */
    public SingleContainerPacker(final Container container) {
        this.containerState = new ContainerState(container);
    }

    /**
     *
     */
    public ContainerState state() {
        return this.containerState;
    }

    /**
     *
     */
    @Override
    public PackItemResult add(final Item item) {

        //Quick & simple tests at first...
        if (item.weight() > this.containerState.remainWeight())
            return PackItemResult.FailedToHeavy;

        if (!Dimension.fitsIn(item, this.containerState.container()))
            return PackItemResult.FailedDoesNotFitIn;

        //Find placement to add item to
        var placement = this.findPlacement(
                this.containerState.placement(),
                item
        );
        if (placement == null)
            return PackItemResult.FailedNoSpaceLeft;

        placement.setItem(item);

        return PackItemResult.Success;
    }

    /**
     * Recursively lookup for next {@link Placement} which has no item.
     */
    protected Placement findPlacement(final Placement placement, final Item item) {

        if (placement.item() == null)
            return placement;

        var remainder = placement.findRemainder(item);
        if (remainder != null)
            return remainder;

        for (var child : placement.children()) {
            var childMatch = this.findPlacement(child, item);
            if (childMatch != null)
                return childMatch;
        }

        return null;
    }
}
