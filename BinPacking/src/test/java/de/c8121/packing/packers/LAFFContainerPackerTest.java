package de.c8121.packing.packers;

import de.c8121.packing.Item;
import de.c8121.packing.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
class LAFFContainerPackerTest extends ContainerPackerTestBase {

    /**
     *
     */
    public static void main(String[] args) throws IOException {
        new LAFFContainerPackerTest().test();
    }

    /**
     *
     */
    public void test() throws IOException {


        this.testListPacking();
        this.testSortingAndRotation();
        this.writeHtml();
    }

    /**
     *
     */
    public void testListPacking() {

        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem());

        var container = new BasicContainer(200, 200, 200, 400);
        container.moveBy(0, -200, 300);
        this.vis.add(container);

        var packer = new LAFFContainerPacker(container);
        this.packList(packer, randomItems);

    }

    /**
     *
     */
    public void testSortingAndRotation() {

        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem());

        this.createContainer(randomItems, 0);

        ItemListSorter.sortByLargestFootprint(randomItems);
        this.createContainer(this.copyItems(randomItems), 300);

        ItemListSorter.sortByLargestFootprintAndLowestHeight(randomItems);
        this.createContainer(this.copyItems(randomItems), 600);

        var rotatedItems = ItemRotator.rotateToLargestFootprintAndLowestHeight(randomItems);
        ItemListSorter.sortByLargestFootprintAndHighestVolumeAndLowestHeight(rotatedItems);
        this.createContainer(rotatedItems, 900);
    }

    /**
     *
     */
    private void createContainer(final List<Item> items, int xOffset) {

        var container = new BasicContainer(200, 200, 200, 400);
        container.moveBy(xOffset, 0, 0);
        this.vis.add(container);

        var info = new BasicPackListResult();
        var packer = new LAFFContainerPacker(container);
        for (var item : items) {
            var result = this.addItem(packer, item);
            info.add(result, item);
        }

        System.out.println(packer);
        System.out.println(info);
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