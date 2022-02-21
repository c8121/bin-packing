package de.c8121.packing.util;

import de.c8121.packing.Item;

import java.util.Comparator;
import java.util.List;

public class ItemListSorter {

    /**
     * Sort given {@link Item}s by largest footprint (the largest area at bottom).
     */
    public static void sortByLargestFootprint(final List<Item> items) {
        items.sort(Comparator.comparingDouble(i -> i.xs() * i.ys() * -1));
    }


    /**
     * Sort given {@link Item}s by largest footprint (the largest area at bottom)
     * and lowest height ({@link Item#zs()}
     */
    public static void sortByLargestFootprintAndLowestHeight(final List<Item> items) {
        items.sort(Comparator.comparingDouble((Item i) -> i.xs() * i.ys() * -1)
                .thenComparing(Item::zs));
    }

}
