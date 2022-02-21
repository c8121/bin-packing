package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Item;
import de.c8121.packing.Placement;
import de.c8121.packing.util.BasicBox;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Default Placement implementation.
 * Based on LAFF-Algorithm, so {@link Item}s should be added in a sorted manner:
 * Items with the largest footprint first.
 * Please use {@link de.c8121.packing.util.ItemListSorter#sortByLargestFootprintAndLowestHeight(List)} for example.
 * <p>
 * Offers one remaining Placement filling the whole box
 * if no {@link Item} was set ({@link #setItem(Item)}.
 * <p>
 * Offers 6 remaining Placement around the {@link Item}
 * after it was set. As remaining Placements overlap, not all can be used,
 * but the best Placement will be chosen by {@link #findPlacement(Item)}.
 * <p>
 * Remainders:
 * <p>
 * A) No item placed
 * <pre><code>
 *   +--------------------------------------+
 *   |          Remainder A                 |
 *   |                                      |
 *   +--------------------------------------+
 *   </code></pre>
 * <p>
 * B-F, Z) Item was placed ({@link #setItem(Item)})
 * <pre><code>
 *   +--------+-----------------------------+
 *   | Item   |          Remainder B        |
 *   | Rem. Z |                             |
 *   +--------+-----------------------------+
 *   |         Remainder C                  |
 *   |                                      |
 *   +--------------------------------------+
 * </code></pre>
 * <pre><code>
 *   +--------+-----------------------------+
 *   | Item   |           Remainder D       |
 *   | Rem. Z |                             |
 *   +--------+                             |
 *   |  Rem.  |                             |
 *   |  E     |                             |
 *   |        |                             |
 *   +--------+-----------------------------+
 * </code></pre>
 * <pre><code>
 *   +--------+
 *   | Item   |
 *   | Rem. Z |
 *   +--------+-----------------------------+
 *            |           Remainder F       |
 *            |                             |
 *            +-----------------------------+
 * </code></pre>
 */
public class LAFFPlacement extends BasicBox implements Box, Placement {

    private final Box parent;
    private Item item;

    /**
     * All placement children which have items.
     */
    private final Set<LAFFPlacement> children = new HashSet<>();

    /**
     * All remainders available to add item to.
     */
    private final Set<LAFFPlacement> remainders = new HashSet<>();

    /**
     *
     */
    private LAFFPlacement(final Box parent, final Box positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        //No item added so far: Whole place remains
        this.remainders.add(this);
    }

    /**
     *
     */
    public LAFFPlacement(final Box parent) {
        this(parent, parent);
    }

    /**
     *
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
     *     <li>Remove intersecting remainders from parent.</li>
     *     <li>Add {@code this} to {@link #parent#children}.</li>
     * </ul>
     */
    public void setItem(final Item item) {

        Objects.requireNonNull(item);
        if (!item.fitsIn(this))
            throw new IllegalArgumentException("Item does not fit");

        this.item = item;
        this.item.placeAt(
                this.x() - this.xs() / 2 + item.xs() / 2,
                this.y() - this.ys() / 2 + item.ys() / 2,
                this.z() - this.zs() / 2 + item.zs() / 2
        );


        this.remainders.clear();

        this.addRemainder(new BasicBox(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                item.y(),
                this.z(),
                this.xs() - item.xs(),
                item.ys(),
                this.zs()
        ));

        this.addRemainder(new BasicBox(
                this.x(),
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                this.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));

        this.addRemainder(new BasicBox(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                this.y(),
                this.z(),
                this.xs() - item.xs(),
                this.ys(),
                this.zs()
        ));

        this.addRemainder(new BasicBox(
                item.x(),
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                item.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));

        this.addRemainder(new BasicBox(
                item.x(),
                item.y(),
                this.z() + item.zs() / 2,
                item.xs(),
                item.ys(),
                this.zs() - item.zs()
        ));

        this.addRemainder(new BasicBox(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                this.xs() - item.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));


        if (this.parent != null && this.parent instanceof LAFFPlacement) {

            var parentPlacement = (LAFFPlacement) this.parent;

            //Add this to children of parent.
            parentPlacement.children.add(this);

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
     * Create and add remainder.
     * Will ignore boxes with size zero one or more axes.
     * Will also ignore boxes which already exist.
     */
    private void addRemainder(final Box box) {

        if (box.xs() == 0 || box.ys() == 0 || box.zs() == 0)
            return;

        for (var existing : this.remainders) {
            if (box.positionEquals(existing) && box.dimensionEquals(existing))
                return;
        }

        var placement = new LAFFPlacement(this, box);
        this.remainders.add(placement);

        //System.out.println("Add remainder to " + this + "\n\t+" + box);
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
     * @return Matching {@link LAFFPlacement} object or {@code null}.
     */
    @Override
    public LAFFPlacement findRemainder(final Box box) {

        Objects.requireNonNull(box);

        LAFFPlacement best = null;
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

    /**
     * Find {@link Placement} holding given {@link Item}.
     * Lookup will be done recursively starting as {@code this}.
     */
    @Override
    public Placement findPlacement(final Item item) {

        if (this.item == item)
            return this;

        for (var child : this.children) {
            var placement = child.findPlacement(item);
            if (placement != null)
                return placement;
        }

        return null;
    }
}
