package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Item;

import java.util.HashMap;
import java.util.Map;

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

    public static enum RemainderPosition {
        A,
        B,
        C,
        D,
        E,
        Z
    }

    private final Box parent;
    private Item item;

    private final Map<RemainderPosition, Placement> remainder = new HashMap<>();

    /**
     *
     */
    private Placement(final Box parent, final Box positionAndDimension) {

        super(positionAndDimension);
        this.parent = parent;

        this.remainder.put(RemainderPosition.A, this);
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
        this.remainder.clear();

        this.remainder.put(RemainderPosition.B, new Placement(
                this,
                new Box(
                        item.x() + item.xs(),
                        item.y(),
                        item.z(),
                        this.parent.xs() - item.xs(),
                        item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.put(RemainderPosition.C, new Placement(
                this,
                new Box(
                        item.x(),
                        item.y() + item.ys(),
                        item.z(),
                        this.parent.xs(),
                        this.parent.ys() - item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.put(RemainderPosition.D, new Placement(
                this,
                new Box(
                        item.x() + item.xs(),
                        item.y(),
                        item.z(),
                        this.parent.xs() - item.xs(),
                        this.parent.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.put(RemainderPosition.E, new Placement(
                this,
                new Box(
                        item.x(),
                        item.y() + item.ys(),
                        item.z(),
                        item.xs(),
                        this.parent.ys() - item.ys(),
                        this.parent.zs()
                )
        ));

        this.remainder.put(RemainderPosition.Z, new Placement(
                this,
                new Box(
                        item.x(),
                        item.y(),
                        item.z() + item.zs(),
                        item.xs(),
                        item.ys(),
                        this.parent.zs() - item.zs()
                )
        ));

    }

    /**
     *
     */
    public Map<RemainderPosition,Placement> getRemainder() {
        return Map.copyOf(this.remainder);
    }
}
