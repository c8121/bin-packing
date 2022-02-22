package de.c8121.packing.packers;

import de.c8121.packing.*;
import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;

import java.util.*;

/**
 * Takes one or more {@link Container}s and packs a list of {@link Item}s.
 * Tries to use as few Containers as possible.
 * Containers will be cloned if more of one type is required
 */
public class ListPacker {

    private final Class<? extends AbstractPacker> packerClass;
    private final Set<Container> availableContainers;

    private final Map<Packer, Map<Packer, List<Item>>> discarded = new HashMap<>();

    /**
     *
     */
    public ListPacker(final Class<? extends AbstractPacker> packerClass, final Set<Container> availableContainers) {
        this.packerClass = packerClass;
        this.availableContainers = availableContainers;
    }

    /**
     *
     */
    public Map<Packer, Map<Packer, List<Item>>> discarded() {
        return discarded;
    }

    /**
     *
     */
    public Map<Packer, List<Item>> pack(final List<Item> items) throws ReflectiveOperationException {

        var result = new LinkedHashMap<Packer, List<Item>>();

        var remainingItems = items;
        while (remainingItems.size() > 0) {

            var chunk = this.packChunk(remainingItems);
            if (chunk == null)
                return null;

            var packer = chunk.getKey();
            var packListResult = chunk.getValue();
            var packedItems = packListResult.get(PackItemResult.Success);
            if (packedItems.size() == 0)
                return null;

            result.put(packer, packedItems);
            System.out.println("*** Next Container: " + packer.container() + " / " + packListResult);

            remainingItems = packListResult.getFailed();
        }

        return result;
    }

    /**
     * Pack all available containers, choose best.
     */
    protected Map.Entry<Packer, PackListResult> packChunk(final List<Item> items) throws ReflectiveOperationException {

        var packedLists = this.packToAllContainers(items);
        return this.selectBest(packedLists);
    }

    /**
     *
     */
    protected Map.Entry<Packer, PackListResult> selectBest(final Map<Packer, PackListResult> packed) {

        if (packed.isEmpty())
            return null;

        Map.Entry<Packer, PackListResult> best = null;
        int bestItemCount = 0;
        double bestFillingLevel = 0;

        var discarded = new LinkedHashMap<Packer, List<Item>>();

        for (var e : packed.entrySet()) {

            var packer = e.getKey();
            var items = e.getValue();
            var state = packer.state();
            var itemCount = state.items().size();
            var itemFillingLevel = state.fillingLevel();

            if (best == null || bestItemCount < itemCount
                    || (bestItemCount == itemCount && itemFillingLevel > bestFillingLevel)
            ) {
                best = e;
                bestItemCount = itemCount;
                bestFillingLevel = itemFillingLevel;
            }

            discarded.put(packer, items.get(PackItemResult.Success));
        }

        discarded.remove(best.getKey());
        this.discarded.put(best.getKey(), discarded);

        return best;
    }

    /**
     *
     */
    protected Map<Packer, PackListResult> packToAllContainers(final List<Item> items) throws ReflectiveOperationException {

        var result = new HashMap<Packer, PackListResult>();

        for (var containerTemplate : availableContainers) {
            var container = new BasicContainer(containerTemplate);
            var packer = this.createPacker(container);
            var packResult = packer.pack(this.copyItems(items));
            result.put(packer, packResult);
        }

        return result;
    }

    /**
     *
     */
    protected List<Item> copyItems(final List<Item> items) {
        var result = new ArrayList<Item>(items.size());
        for (var item : items) {
            result.add(new BasicItem(item));
        }
        return result;
    }

    /**
     *
     */
    protected Packer createPacker(final Container container) throws ReflectiveOperationException {
        return this.packerClass
                .getConstructor(Container.class)
                .newInstance(container);
    }

}
