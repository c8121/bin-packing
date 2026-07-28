package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Item;
import de.c8121.packing.Placement;
import de.c8121.packing.util.BasicBox;

import java.util.*;

/**
 *
 */
public abstract class AbstractPlacement extends BasicBox implements Box, Placement {

    private final Box parent;
    protected Item item;

    /**
     * All placement children which have items.
     * Only children having an Item (no empty remainders).
     */
    private final Set<AbstractPlacement> children = new HashSet<>();

    /**
     * All remainders available to add item to.
     */
    private final Set<AbstractPlacement> remainders = new LinkedHashSet<>();

    /**
     *
     */
    protected AbstractPlacement(final Box parent, final Box positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        //No item added so far: Whole place remains
        this.remainders.add(this);
    }

    /**
     * Get the parent Placement, if available and of type {@link AbstractPlacement}.
     *
     * @return {@link #parent()} if it is a {@link Placement}, <code>null</code> otherwise.
     */
    protected AbstractPlacement parentPlacement() {
        if (parent instanceof AbstractPlacement parentPlacement)
            return parentPlacement;
        return null;
    }

    /**
     * @return The one item that has been added to this with {@link #setItem(Item)}
     */
    @Override
    public Item item() {
        return this.item;
    }

    /**
     *
     */
    @Override
    public Box parent() {
        return this.parent;
    }

    /**
     *
     */
    @Override
    public Set<Placement> children() {
        return Set.copyOf(this.children);
    }


    /**
     * <ul>
     *     <li>Set item to {@code this}.</li>
     *     <li>Create remainders.</li>
     *     <li>Remove invalid (intersecting) remainders from parent.</li>
     *     <li>Add {@code this} to {@link #parent#children}.</li>
     * </ul>
     */
    public void setItem(final Item item) {

        Objects.requireNonNull(item);
        if (this.item != null)
            throw new IllegalStateException("Item was already set");

        this.placeItem(item);
        this.registerChild();

        //Reset remainders, which will at least be the initial remainder (A)
        this.remainders.clear();
        this.createRemainders();

        this.removeInvalidRemainders();
    }

    /**
     * Place the item within this (choose position)
     */
    protected abstract void placeItem(final Item item);

    /**
     * Register this as child of parent after an item was placed.
     * See {@link #children()}
     */
    protected void registerChild() {
        var parentPlacement = parentPlacement();
        if (parentPlacement != null) {
            parentPlacement.children.add(this);
        }
    }

    /**
     * Create remainders after item was placed.
     */
    protected abstract void createRemainders();

    /**
     * Utility method to create and add a remainder at given position.
     * Will ignore boxes with size zero on one or more axes.
     * Will also ignore boxes which already exist.
     */
    protected void addRemainder(final Box box) {

        if (box.xs() == 0 || box.ys() == 0 || box.zs() == 0)
            return;

        for (var existing : this.remainders) {
            if (box.positionEquals(existing) && box.dimensionEquals(existing))
                return;
        }

        var placement = createRemainder(this, box);
        this.remainders.add(placement);

        //System.out.println("Add remainder to " + this + "\n\t+" + box);
    }

    /**
     * Create a new AbstractPlacement instance.
     */
    protected abstract AbstractPlacement createRemainder(Box parent, Box positionAndDimension);

    /**
     * Remove remainders that have become invalid (intersecting with newly created remainders)
     */
    protected void removeInvalidRemainders() {

        var parentPlacement = parentPlacement();
        if (parentPlacement != null) {

            //Remove remainders from parent that intersect with remainders from this
            var iterator = parentPlacement.remainders.iterator();
            while (iterator.hasNext()) {
                var parentRemainder = iterator.next();
                for (var remainder : this.remainders) {
                    if (remainder.intersects(parentRemainder)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }


    /**
     *
     */
    @Override
    public List<Placement> remainders() {
        return List.copyOf(this.remainders);
    }

    /**
     * Find remainder where given {@code box} fits in.
     * Checks all remainders of {@code this} (see {@link #remainders()}), not recursive.
     *
     * @return Matching {@link AbstractPlacement} object or {@code null}.
     */
    @Override
    public AbstractPlacement findRemainder(final Box box) {

        Objects.requireNonNull(box);

        AbstractPlacement best = null;
        double bestScore = 0;

        for (var remainder : this.remainders) {
            if (box.fitsIn(remainder)) {

                // Consider better if box uses more space on x- or y-axis

                var currentScore = Math.max(
                        (double) box.xs() / remainder.xs(),
                        (double) box.ys() / remainder.ys()
                );

                if (best == null || bestScore < currentScore) {
                    best = remainder;
                    bestScore = currentScore;
                }
            }
        }

        return best;
    }
}
