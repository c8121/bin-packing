package de.c8121.packing.packers;

import de.c8121.packing.Box;
import de.c8121.packing.Item;
import de.c8121.packing.util.BasicBox;

import java.util.List;

/**
 * Default Placement implementation.
 * Based on LAFF-Algorithm, so {@link Item}s should be added in a sorted manner:
 * Items with the largest footprint first.
 * Please use {@link de.c8121.packing.util.ItemListSorter#sortByLargestFootprintAndLowestHeight(List)} for example.
 * <p>
 * See: <a href="http://www.zahidgurbuz.com/yayinlar/An%20Efficient%20Algorithm%20for%203D%20Rectangular%20Box%20Packing.pdf">zahidgurbuz.com: Algorithm for Rectangular BoxPacking</a>
 * <p>
 * Offers one remaining Placement filling the whole box
 * if no {@link Item} was set ({@link #setItem(Item)}).
 * <p>
 * Offers 6 remaining Placement around the {@link Item}
 * after it was set. As remaining Placements overlap, not all can be used,
 * but the best Placement will be chosen by {@link #findRemainder(Box)}.
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
 * (Remainder Z is placed above Item)
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
public class LAFFPlacement extends AbstractPlacement {

    /**
     *
     */
    protected LAFFPlacement(final Box parent, final Box positionAndDimension) {
        super(parent, positionAndDimension);
    }

    /**
     *
     */
    public LAFFPlacement(final Box parent) {
        this(parent, parent);
    }

    /**
     * Place the item within this
     */
    @Override
    protected void placeItem(final Item item) {

        if (!item.fitsIn(this))
            throw new IllegalArgumentException("Item does not fit");

        this.item = item;
        this.item.placeAt(
                this.x() - this.xs() / 2 + item.xs() / 2,
                this.y() - this.ys() / 2 + item.ys() / 2,
                this.z() - this.zs() / 2 + item.zs() / 2
        );
    }

    /**
     * Create remainders after item was placed.
     */
    @Override
    protected void createRemainders() {

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
    }

    /**
     * Create a new LAFFPlacement as remainder.
     */
    @Override
    protected AbstractPlacement createRemainder(Box parent, Box positionAndDimension) {
        return new LAFFPlacement(parent, positionAndDimension);
    }
}
