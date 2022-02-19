package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.visualization.ThreeJs;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class PlacementTest {

    private final ThreeJs vis = new ThreeJs();

    /**
     *
     */
    public static void main(String[] args) throws IOException {
        new PlacementTest().test();
    }

    /**
     *
     */
    public void test() throws IOException {

        var container = new Container(200, 200, 200, 100);
        this.vis.add(container);

        var placement = new Placement(container);
        //this.vis.add(placement);

        var item = new Item(50, 70, 90, 10);
        this.vis.add(item);
        this.vis.setStyle(item, "color: 0xffff00, wireframe: false");

        placement.setItem(item);

        item = new Item(90, 90, 90, 10);
        this.vis.add(item);
        this.vis.setStyle(item, "color: 0x00ff00, wireframe: false");

        var nextPlacement = placement.findRemainder(item);
        nextPlacement.setItem(item);

        this.showRemainders(placement);
        this.showRemainders(nextPlacement);

        this.showAxis();

        var file = new File(FileUtils.getTempDirectory(), this.getClass().getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        this.vis.writeHtml(file);

    }

    /**
     *
     */
    private void showRemainders(final Placement placement) {
        int num = 0;
        for (var remainder : placement.getRemainders()) {

            this.vis.add(remainder);

            num++;
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
     *
     */
    private void showAxis() {
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