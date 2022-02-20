package de.c8121.packing.packers;

import de.c8121.packing.Item;
import de.c8121.packing.visualization.ThreeJs;

public abstract class ThreeJsTestBase {

    protected final ThreeJs vis = new ThreeJs();

    /**
     *
     */
    public void showRemainders(final Placement placement) {
        int num = 0;
        for (var remainder : placement.remainders()) {

            num++;
            this.vis.add(remainder);

            if (num < 3)
                this.vis.setStyle(remainder, "color: 0xffff00, wireframe: false, opacity: 0.25, transparent: true");
            else if (num < 5)
                this.vis.setStyle(remainder, "color: 0x00ff00, wireframe: false, opacity: 0.25, transparent: true");
            else if (num < 7)
                this.vis.setStyle(remainder, "color: 0x0000ff, wireframe: false, opacity: 0.25, transparent: true");
            else if (num == 9)
                num = 0;
        }
    }

    /**
     * Recursively show all remainders.
     */
    public void showAllRemainders(final Placement placement) {

        this.showRemainders(placement);

        for (var child : placement.children()) {
            showAllRemainders(child);
        }

    }

    /**
     *
     */
    public void showAxis() {

        var axis = new Item(900, 1, 1, 0);
        this.vis.add(axis);
        this.vis.setStyle(axis, "color: 0xff0000");

        axis = new Item(1, 900, 1, 0);
        this.vis.add(axis);
        this.vis.setStyle(axis, "color: 0x00ff00");

        axis = new Item(1, 1, 900, 0);
        this.vis.add(axis);
        this.vis.setStyle(axis, "color: 0x0000ff");
    }
}
