package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
public class ContainerPackerTest extends ContainerPackerTestBase {

    /**
     *
     */
    public static void main(String[] args) throws IOException {
        new ContainerPackerTest().test();
    }

    /**
     *
     */
    public void test() throws IOException {

        var container = new BasicContainer(200, 200, 200, 400);
        this.vis.add(container);

        var packer = new LAFFContainerPacker(container);

        var item = new BasicItem(50, 50, 15, 10);
        this.addItem(packer, item);

        //this.showRemainders(this.packer.state().placement().findPlacement(item));
        //this.vis.copyTo(450, 0, 0);

        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem());

        this.packContainer(packer, randomItems);

        //this.showRemainders(this.packer.state().placement().findPlacement(item));
        //this.showAllRemainders(this.packer.state().placement());

        this.writeHtml();

    }
}