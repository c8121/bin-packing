package de.c8121.packing;

import java.util.ArrayList;
import java.util.List;

public interface PackListResult {

    /**
     * Get all items for which {@link Packer#add(Item)} returned given result.
     * Does not return {@code null} but a empty list if no item was found.
     */
    List<Item> get(final PackItemResult packItemResult);

    /**
     * Get all items for which {@link Packer#add(Item)} did not return {@link PackItemResult#Success}.
     * Does not return {@code null} but a empty list if no item was found.
     */
    default List<Item> getFailed() {
        var result = new ArrayList<Item>();
        for (var packItemResult : PackItemResult.values()) {
            if (packItemResult != PackItemResult.Success)
                result.addAll(this.get(packItemResult));
        }
        return result;
    }

    /**
     *
     */
    int totalVolume();

    /**
     *
     */
    int totalWeight();
}
