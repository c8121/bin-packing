package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Item;

import java.util.ArrayList;
import java.util.List;

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

    private final List<Placement> remainder = new ArrayList<>();

    /**
     *
     */
    private Placement(final Box parent, final Box positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        //No item added so far: Whole place remains
        this.remainder.add(this);
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

        this.item = item;
        this.item.move(
                this.x() - this.xs() / 2 + item.xs() / 2,
                this.y() - this.ys() / 2 + item.ys() / 2,
                this.z() - this.zs() / 2 + item.zs() / 2
        );


        this.remainder.clear();

        this.remainder.add(new Placement(
                this,
                new Box(
                        item.x() + item.xs() / 2 + (this.parent.xs() - item.xs()) / 2,
                        item.y(),
                        this.parent.z(),
                        this.parent.xs() - item.xs(),
                        item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.add(new Placement(
                this,
                new Box(
                        this.parent.x(),
                        item.y() + item.ys() / 2 + (this.parent.ys() - item.ys()) / 2,
                        this.parent.z(),
                        this.parent.xs(),
                        this.parent.ys() - item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.add(new Placement(
                this,
                new Box(
                        item.x() + item.xs() / 2 + (this.parent.xs() - item.xs()) / 2,
                        this.parent.y(),
                        this.parent.z(),
                        this.parent.xs() - item.xs(),
                        this.parent.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.add(new Placement(
                this,
                new Box(
                        item.x(),
                        item.y() + item.ys() / 2 + (this.parent.ys() - item.ys()) / 2,
                        this.parent.z(),
                        item.xs(),
                        this.parent.ys() - item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.add(new Placement(
                this,
                new Box(
                        item.x(),
                        item.y(),
                        this.parent.z() + item.zs() / 2,
                        item.xs(),
                        item.ys(),
                        this.parent.zs() - item.zs()
                )
        ));

    }

    /**
     *
     */
    public List<Placement> getRemainder() {
        return List.copyOf(this.remainder);
    }
}
