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

    private final Map<Packer, List<Item>> discarded = new LinkedHashMap<>();

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
    public Map<Packer, List<Item>> discarded() {
        return discarded;
    }

    /**
     *
     */
    public Map<Container, List<Item>> pack(final List<Item> items) throws ReflectiveOperationException {

        var result = new LinkedHashMap<Container, List<Item>>();

        var remainingItems = items;
        while (remainingItems.size() > 0) {

            var chunk = this.packChunk(remainingItems);
            if (chunk == null)
                return null;

            var packListResult = chunk.getValue();
            var packedItems = packListResult.get(PackItemResult.Success);
            if (packedItems.size() == 0)
                return null;

            result.put(chunk.getKey().container(), packedItems);
            System.out.println("*** Next Container: " + chunk.getKey());
            System.out.println("\n" + packedItems);

            remainingItems = packListResult.getFailed();
        }

        return result;
    }

    /**
     * Pack all available containers, choose best.
     */
    protected Map.Entry<Packer, PackListResult> packChunk(final List<Item> items) throws ReflectiveOperationException {

        var packedLists = this.packToAllContainers(items);

        Map.Entry<Packer, PackListResult> best = null;
        double bestScore = 0;
        for (var e : packedLists.entrySet()) {

            var packer = e.getKey();
            var container = packer.container();
            var resultList = e.getValue();
            var score = this.computeScore(container, resultList);

            System.out.println("Container: " + container + ", score=" + score);
            System.out.println("\t" + resultList);

            if (best == null || score > bestScore) {
                if (best != null)
                    this.discarded.remove(best.getKey());
                best = e;
                bestScore = score;
            } else {
                this.discarded.put(e.getKey(), resultList.get(PackItemResult.Success));
            }
        }

        if (best != null) {
            System.out.println("Best: " + best.getKey().container() + ", score=" + bestScore);
            System.out.println("\t" + packedLists.get(best.getKey()));
        }

        return best;
    }

    /**
     *
     */
    protected double computeScore(final Container container, final PackListResult result) {
        //TODO: Score lowest remaining space...
        return result.get(PackItemResult.Success).size();
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
