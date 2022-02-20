package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Dimension;
import de.c8121.packing.Item;
import de.c8121.packing.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Remainders:
 * <p>
 * A) No item placed
 * <pre><code>
 *   +----------------------------------------------+
 *   |                                              |
 *   |                  Remainder A                 |
 *   |                                              |
 *   |                                              |
 *   |                                              |
 *   |                                              |
 *   |                                              |
 *   |                                              |
 *   |                                              |
 *   +----------------------------------------------+
 *   </code></pre>
 * B)
 *
 * <pre><code>
 *   +--------+-------------------------------------+
 *   |        |                                     |
 *   | Item   |          Remainder B                |
 *   | Rem. Z |                                     |
 *   +--------+-------------------------------------+
 *   |                                              |
 *   |                                              |
 *   |                Remainder C                   |
 *   |                                              |
 *   |                                              |
 *   +----------------------------------------------+
 * </code></pre>
 * <p>
 * C)
 *
 * <pre><code>
 *   +--------+-------------------------------------+
 *   |        |                                     |
 *   | Item   |           Remainder D               |
 *   | Rem. Z |                                     |
 *   +--------+                                     |
 *   |        |                                     |
 *   |  Rem.  |                                     |
 *   |  E     |                                     |
 *   |        |                                     |
 *   |        |                                     |
 *   +--------+-------------------------------------+
 * </code></pre>
 * <p>
 * D)
 *
 * <pre><code>
 *   +--------+
 *   |        |
 *   | Item   |
 *   | Rem. Z |
 *   +--------+-------------------------------------+
 *            |                                     |
 *            |                                     |
 *            |           Remainder F               |
 *            |                                     |
 *            |                                     |
 *            +-------------------------------------+
 * </code></pre>
 */
public class Placement extends Box {

    private final Box parent;
    private Item item;

    /**
     * All placement children which have items.
     */
    private final Set<Placement> children = new HashSet<>();

    /**
     * All remainders available to add item to.
     */
    private final Set<Placement> remainders = new HashSet<>();

    /**
     *
     */
    private Placement(final Box parent, final Box positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        //No item added so far: Whole place remains
        this.remainders.add(this);
    }

    /**
     *
     */
    public Placement(final Box parent) {
        this(parent, parent);
    }

    /**
     *
     */
    public Set<Placement> children() {
        return Set.copyOf(this.children);
    }

    /**
     *
     */
    public Item item() {
        return this.item;
    }

    /**
     * <ul>
     * <li>Set item to {@code this}.</li>
     * <li>Create remainders.</li>
     * <li>Remove intersecting remainders from parent.</li>
     * <li>Add {@code this} to {@link #parent#children}.</li>
     * </ul>
     */
    public void setItem(final Item item) {

        Objects.requireNonNull(item);
        if (!Dimension.fitsIn(item, this))
            throw new IllegalArgumentException("Item does not fit");

        this.item = item;
        this.item.placeAt(
                this.x() - this.xs() / 2 + item.xs() / 2,
                this.y() - this.ys() / 2 + item.ys() / 2,
                this.z() - this.zs() / 2 + item.zs() / 2
        );


        this.remainders.clear();

        this.addRemainder(new Box(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                item.y(),
                this.z(),
                this.xs() - item.xs(),
                item.ys(),
                this.zs()
        ));

        this.addRemainder(new Box(
                this.x(),
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                this.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));

        this.addRemainder(new Box(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                this.y(),
                this.z(),
                this.xs() - item.xs(),
                this.ys(),
                this.zs()
        ));

        this.addRemainder(new Box(
                item.x(),
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                item.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));

        this.addRemainder(new Box(
                item.x(),
                item.y(),
                this.z() + item.zs() / 2,
                item.xs(),
                item.ys(),
                this.zs() - item.zs()
        ));

        this.addRemainder(new Box(
                item.x() + item.xs() / 2 + (this.xs() - item.xs()) / 2,
                item.y() + item.ys() / 2 + (this.ys() - item.ys()) / 2,
                this.z(),
                this.xs() - item.xs(),
                this.ys() - item.ys(),
                this.zs()
        ));


        if (this.parent != null && this.parent instanceof Placement) {

            var parentPlacement = (Placement) this.parent;

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
            if (Position.positionEquals(box, existing) && Dimension.dimensionEquals(box, existing))
                return;
        }

        var placement = new Placement(this, box);
        this.remainders.add(placement);

        //System.out.println("Add remainder to " + this + "\n\t+" + box);
    }

    /**
     *
     */
    public List<Placement> remainders() {
        return List.copyOf(this.remainders);
    }

    /**
     * Find remainder where given {@code box} fits in.
     * Checks all remainders of {@code this} (see {@link #remainders()}), not recursive.
     *
     * @return Matching {@link Placement} object or {@code null}.
     */
    public Placement findRemainder(final Box box) {

        Objects.requireNonNull(box);

        for (var remainder : this.remainders) {
            if (Dimension.fitsIn(box, remainder))
                return remainder;
        }

        return null;
    }

    /**
     * Find {@link Placement} holding given {@link Item}
     */
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
