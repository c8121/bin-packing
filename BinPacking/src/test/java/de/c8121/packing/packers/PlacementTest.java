package de.c8121.packing.packers;

import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;

import java.io.IOException;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
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

        var container = new BasicContainer(200, 200, 200, 100);
        this.vis.add(container);

        var placement = new LAFFPlacement(container);
        //this.vis.add(placement);

        var item = new BasicItem(50, 70, 90, 10);
        this.vis.add(item);
        this.vis.setStyle(item, "color: 0xffff00, wireframe: false");

        placement.setItem(item);

        item = new BasicItem(90, 90, 90, 10);
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