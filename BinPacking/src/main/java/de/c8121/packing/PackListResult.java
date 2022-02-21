package de.c8121.packing;

import java.util.List;

public interface PackListResult {

    /**
     *
     */
    List<Item> get(final PackItemResult packItemResult);

    /**
     *
     */
    int totalVolume();

    /**
     *
     */
    int totalWeight();
}
