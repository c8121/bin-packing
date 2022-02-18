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

        var container = new Container(200, 200, 200, 100);
        vis.add(container);

        var placement = new Placement(container);
        //vis.add(placement);

        var item = new Item(30, 30, 30, 10);
        vis.add(item);
        placement.setItem(item);

        for (var remainder : placement.getRemainder().entrySet()) {
            var pos = remainder.getKey();
            vis.add(remainder.getValue());
            switch (pos) {
                case B, C -> vis.setStyle(remainder.getValue(), "color: 0xffff00, wireframe: false, opacity: 0.25, transparent: true");
                case D, E -> vis.setStyle(remainder.getValue(), "color: 0xffff00, wireframe: true, opacity: 0.25, transparent: true");
                case Z -> vis.setStyle(remainder.getValue(), "color: 0xffff00, wireframe: false, opacity: 0.25, transparent: true");
            }
        }

        var file = new File(FileUtils.getTempDirectory(), PlacementTest.class.getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        vis.writeHtml(file);

    }

}