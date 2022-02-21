package de.c8121.packing;


import java.util.List;

/**
 * Packs {@link Item}s into one {@link Container}.
 */
public interface Packer {

    /**
     * Try to pack all of given {@link Item}s to {@link Container}.
     */
    PackListResult pack(final List<Item> items);

    /**
     * Try to add given {@link Item} to {@link Container}.
     * <p>
     * <b>Note</b>: A Packer implementation might require sorting of items to
     * achieve best results. So using {@link #pack(List)} might be better.
     */
    PackItemResult add(final Item item);


    /**
     * Current state of packing.
     */
    ContainerState state();
}
