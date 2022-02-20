package de.c8121.packing.util;

import de.c8121.packing.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemListSorter {

    /**
     * Sort given {@link Item}s by largest footprint (the largest area at bottom).
     */
    public static void sortByLargestFootprint(final List<Item> items) {
        items.sort(Comparator.comparingDouble(i -> i.xs() * i.ys() * -1));
    }

}
