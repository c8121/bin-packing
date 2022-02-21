package de.c8121.packing.packers;

import de.c8121.packing.*;
import de.c8121.packing.util.BasicContainerState;
import de.c8121.packing.util.BasicPackListResult;
import de.c8121.packing.util.ItemListSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Default {@link de.c8121.packing.Packer} implementation.
 * Based on LAFF-Algorithm, so {@link Item}s should be added in a sorted manner:
 * Items with the largest footprint first.
 * Please use {@link de.c8121.packing.util.ItemListSorter#sortByLargestFootprintAndLowestHeight(List)} for example.
 * <p>
 * See: http://www.zahidgurbuz.com/yayinlar/An%20Efficient%20Algorithm%20for%203D%20Rectangular%20Box%20Packing.pdf
 */
public class LAFFContainerPacker implements Packer {

    private final BasicContainerState containerState;
    private final LAFFPlacement rootPlacement;


    /**
     *
     */
    public LAFFContainerPacker(final Container container) {
        this.containerState = new BasicContainerState(container);
        this.rootPlacement = new LAFFPlacement(container);
    }

    /**
     *
     */
    @Override
    public ContainerState state() {
        return this.containerState;
    }


    /**
     *
     */
    @Override
    public PackListResult pack(List<Item> items) {

        var sortedItems = new ArrayList<>(items);
        ItemListSorter.sortByLargestFootprintAndLowestHeight(sortedItems);

        var result = new BasicPackListResult();

        for (var item : sortedItems) {
            var itemResult = this.add(item);
            result.add(itemResult, item);
        }

        return result;
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
