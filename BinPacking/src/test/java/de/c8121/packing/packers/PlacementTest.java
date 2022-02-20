package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class PlacementTest extends ThreeJsTestBase {

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

        //this.showRemainders(placement);
        this.showRemainders(nextPlacement);

        this.showAxis();

        this.writeHtml();
    }
}