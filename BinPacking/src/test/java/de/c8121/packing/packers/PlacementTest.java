package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.visualization.ThreeJs;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class PlacementTest {

    /**
     *
     */
    public static void main(String[] args) throws IOException {

        var vis = new ThreeJs();

        var axis = new Item(900, 1, 1, 0);
        vis.add(axis);
        vis.setStyle(axis, "color: 0xff0000");

        axis = new Item(1, 900, 1, 0);
        vis.add(axis);
        vis.setStyle(axis, "color: 0x00ff00");

        axis = new Item(1, 1, 900, 0);
        vis.add(axis);
        vis.setStyle(axis, "color: 0x0000ff");

        var container = new Container(200, 200, 200, 100);
        vis.add(container);

        var placement = new Placement(container);
        //vis.add(placement);

        var item = new Item(50, 70, 90, 10);
        vis.add(item);
        vis.setStyle(item, "color: 0xffff00, wireframe: false");

        placement.setItem(item);

        for (var remainder : placement.getRemainder().entrySet()) {
            var pos = remainder.getKey();
            switch (pos) {
                case B, C -> {
                    vis.add(remainder.getValue());
                    vis.setStyle(remainder.getValue(), "color: 0xffff00, wireframe: false, opacity: 0.25, transparent: true");
                }
                case D, E -> {
                    vis.add(remainder.getValue());
                    vis.setStyle(remainder.getValue(), "color: 0x00ff00, wireframe: false, opacity: 0.25, transparent: true");
                }
                case Z -> {
                    vis.add(remainder.getValue());
                    vis.setStyle(remainder.getValue(), "color: 0x0000ff, wireframe: false, opacity: 0.25, transparent: true");
                }
            }
        }

        var file = new File(FileUtils.getTempDirectory(), PlacementTest.class.getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        vis.writeHtml(file);

    }

}