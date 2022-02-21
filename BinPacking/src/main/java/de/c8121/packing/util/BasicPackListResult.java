package de.c8121.packing.util;

import de.c8121.packing.Item;
import de.c8121.packing.PackItemResult;
import de.c8121.packing.PackListResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicPackListResult implements PackListResult {

    private final Map<PackItemResult, List<Item>> items = new HashMap<>();

    private int totalVolume = 0;
    private int totalWeight = 0;

    /**
     *
     */
    public void add(final PackItemResult packItemResult, final Item item) {
        var list = this.items.computeIfAbsent(
                packItemResult, (k) -> new ArrayList<>()
        );
        list.add(item);

        if (packItemResult == PackItemResult.Success) {
            this.totalVolume += item.xs() * item.ys() * item.zs();
            this.totalWeight += item.weight();
        }
    }

    /**
     *
     */
    public List<Item> get(final PackItemResult packItemResult) {
        var list = items.get(packItemResult);
        if (list == null)
            return List.of();
        return List.copyOf(list);
    }

    /**
     *
     */
    @Override
    public int totalVolume() {
        return this.totalVolume;
    }

    /**
     *
     */
    @Override
    public int totalWeight() {
        return this.totalWeight;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        for (var e : this.items.entrySet()) {
            if (s.length() > 0)
                s.append(", ");

            s.append(e.getKey())
                    .append(": ")
                    .append(e.getValue().size());
        }

        return "BasicPackListResult{" +
                s +
                ", totalVolume=" + totalVolume +
                ", totalWeight=" + totalWeight +
                '}';
    }
}
