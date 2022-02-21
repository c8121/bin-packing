package de.c8121.packing.visualization;

import de.c8121.packing.util.BasicItem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
class ThreeJsTest {

    /**
     *
     */
    public static void main(String[] args) throws IOException {

        var vis = new ThreeJs();

        var item = new BasicItem(900, 1, 1, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0xff0000");

        item = new BasicItem(1, 900, 1, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0xffff00");

        item = new BasicItem(1, 1, 900, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0x0000ff");

        var file = new File(FileUtils.getTempDirectory(), ThreeJsTest.class.getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        vis.writeHtml(file);

    }

}