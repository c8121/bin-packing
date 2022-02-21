package de.c8121.packing;

import java.util.List;
import java.util.Set;

/**
 * A {@code Placement} can take one {@link Item}.
 * It will offer remaining {@code Placement}s to take further {@code Items}.
 * Best remaining {@code Placement} for next {@code Item} will bee chosen by {@link #findRemainder(Box)}
 */
public interface Placement extends Box {

    /**
     * A Placement can have one {@link Item}, which can be obtained here.
     * Further Items must be placed as children, see {@link #children()}
     */
    Item item();

    /**
     * The parent of {@code this} can be either a {@link Box} ({@link Container} for example)
     * or a {@link Placement}.
     */
    Box parent();

    /**
     * Get child-placements having an {@link Item}.
     */
    Set<Placement> children();


    /**
     * <ul>
     *     <li>Set item to {@code this}.</li>
     *     <li>Create remainders.</li>
     *     <li>Add {@code this} to {@link #children()}.</li>
     * </ul>
     */
    void setItem(final Item item);

    /**
     * All remaining {@link Placement}s to take {@link Item}s
     */
    List<Placement> remainders();


    /**
     * Choose best remaining {@link Placement} for given {@link Box}.
     * Checks all remainders of {@code this} (see {@link #remainders()}), not recursive.
     */
    Placement findRemainder(final Box box);


    /**
     * Find {@link Placement} holding given {@link Item}
     */
    Placement findPlacement(final Item item);

}
