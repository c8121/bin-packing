package de.c8121.packing.visualization;

import de.c8121.packing.Item;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class ThreeJsTest {

    /**
     *
     */
    public static void main(String[] args) throws IOException {

        var vis = new ThreeJs();

        var item = new Item(900, 1, 1, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0xff0000");

        item = new Item(1, 900, 1, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0xffff00");

        item = new Item(1, 1, 900, 0);
        vis.add(item);
        vis.setStyle(item, "color: 0x0000ff");

        var file = new File(FileUtils.getTempDirectory(), ThreeJsTest.class.getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        vis.writeHtml(file);

    }

}