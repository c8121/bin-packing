package de.c8121.packing.util;

import de.c8121.packing.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemRotator {

    /**
     * Create new Item, rotated to larges footprint and lowest height.
     * If no rotation was needed, a copy equally to given item will be returned.
     */
    public static Item rotateToLargestFootprintAndLowestHeight(final Item item) {

        var result = new BasicItem(item);

        if (result.ys() < result.zs()) {
            result = new BasicItem(
                    result.xs(),
                    result.zs(),
                    result.ys(),
                    result.weight()
            );
        }

        if (result.xs() < result.zs()) {
            result = new BasicItem(
                    result.zs(),
                    result.ys(),
                    result.xs(),
                    result.weight()
            );
        }

        return result;
    }

    /**
     * See {@link #rotateToLargestFootprintAndLowestHeight(Item)}
     */
    public static List<Item> rotateToLargestFootprintAndLowestHeight(final List<Item> items) {
        var result = new ArrayList<Item>(items.size());
        for (var item : items)
            result.add(rotateToLargestFootprintAndLowestHeight(item));
        return result;
    }

}
