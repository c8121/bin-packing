package de.c8121.packing.visualization;

import de.c8121.packing.util.BasicItem;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in user-home directory containing a THREE.js scene.
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

        var dir = new File(System.getProperty("user.home"));
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("User-Home not found (" + System.getProperty("user.home") + "), using temp dir");
            dir = FileUtils.getTempDirectory();
        }
        var file = new File(dir, ThreeJsTest.class.getSimpleName() + ".html");
        for (int num = 1; num < 10_000 && file.exists(); num++) {
            file = new File(dir, ThreeJsTest.class.getSimpleName() + "-" + num + ".html");
        }
        if (file.exists())
            throw new IOException("Failed to find uniq file name");

        System.out.println("Writing to " + file);
        vis.writeHtml(file);

    }

}