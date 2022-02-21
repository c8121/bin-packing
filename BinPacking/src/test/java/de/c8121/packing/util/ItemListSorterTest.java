package de.c8121.packing.util;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.packers.ContainerPackerTestBase;
import de.c8121.packing.packers.LAFFContainerPacker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
class ItemListSorterTest extends ContainerPackerTestBase {


    /**
     *
     */
    public static void main(String[] args) throws IOException {
        new ItemListSorterTest().test();
    }

    /**
     *
     */
    public void test() throws IOException {

        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem());

        this.createContainer(randomItems, 0);

        ItemListSorter.sortByLargestFootprint(randomItems);
        this.createContainer(this.copyItems(randomItems), 450);

        this.writeHtml();
    }

    /**
     *
     */
    private void createContainer(final List<Item> items, int xOffset) {

        var container = new BasicContainer(200, 200, 200, 400);
        container.moveBy(xOffset, 0, 0);
        this.vis.add(container);

        var packer = new LAFFContainerPacker(container);
        this.packContainer(packer, items);
    }

    /**
     *
     */
    private List<Item> copyItems(final List<Item> items) {
        var result = new ArrayList<Item>(items.size());
        for (var item : items)
            result.add(new BasicItem(item));
        return result;
    }
}