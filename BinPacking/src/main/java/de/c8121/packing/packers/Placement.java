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
 *   +-------+--------------------------------------+
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
 */
public class Placement extends Box {

    private final Box parent;
    private Item item;

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
    public void setItem(final Item item) {

        Objects.requireNonNull(item);
        if (!Dimension.fitsIn(item, this))
            throw new IllegalArgumentException("Item does not fit");

        this.item = item;
        this.item.move(
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

        //Remove remainders from parent that intersect with remainders from this
        if (this.parent != null && this.parent instanceof Placement) {
            var parentPlacement = (Placement) this.parent;
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

        this.remainders.add(new Placement(this, box));
    }

    /**
     *
     */
    public List<Placement> getRemainders() {
        return List.copyOf(this.remainders);
    }

    /**
     * Find remainder where given {@code box} fits in.
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
}
