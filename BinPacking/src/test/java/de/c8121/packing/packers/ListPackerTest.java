package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Not an automated test. Used from IDE for testing.
 * Creates html-file in temp directory containing a THREE.js scene.
 */
class ListPackerTest extends ContainerPackerTestBase {

    /**
     *
     */
    public static void main(String[] args) throws ReflectiveOperationException, IOException {
        new ListPackerTest().test();
    }

    /**
     *
     */
    public void test() throws ReflectiveOperationException, IOException {

        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < 220; i++)
            randomItems.add(this.createRandomItem());

        var containers = new HashSet<Container>();
        for (int i = 0; i < 10; i++)
            containers.add(this.createRandomContainer());

        var listPacker = new ListPacker(LAFFContainerPacker.class, containers);
        var result = listPacker.pack(randomItems);

        showContainer(result, 0);
        //showContainer(listPacker.discarded(), 400);

        this.writeHtml();
    }

    /**
     *
     */
    private void showContainer(final Map<Container, List<Item>> packedContainers, int zOffset) {

        int offset = 0;
        Container lastContainer = null;
        for (var e : packedContainers.entrySet()) {

            var container = e.getKey();
            var items = e.getValue();

            if (lastContainer != null) {
                offset += lastContainer.xs() / 2;
                offset += container.xs() / 2 + 50;
            }

            container.moveBy(offset, 0, zOffset);
            this.vis.add(container);

            for (var item : items) {
                item.moveBy(offset, 0, zOffset);
                this.vis.add(item);
            }

            lastContainer = container;
        }
    }
}