package de.c8121.packing.packers;

import de.c8121.packing.*;

public class LAFFContainerPacker implements Packer {

    private final ContainerState containerState;
    private final LAFFPlacement rootPlacement;


    /**
     *
     */
    public LAFFContainerPacker(final Container container) {
        this.containerState = new ContainerState(container);
        this.rootPlacement = new LAFFPlacement(container);
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

        if (!item.fitsIn(this.containerState.container()))
            return PackItemResult.FailedDoesNotFitIn;

        //Find placement to add item to
        var placement = this.findPlacement(
                this.rootPlacement,
                item
        );
        if (placement == null)
            return PackItemResult.FailedNoSpaceLeft;

        placement.setItem(item);
        this.containerState.addItem(item);

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
