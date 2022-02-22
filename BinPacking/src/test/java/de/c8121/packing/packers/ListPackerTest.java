package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.Packer;
import de.c8121.packing.util.BasicContainer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

        var random = ThreadLocalRandom.current();
        var maxItems = random.nextInt(100, 250);
        var randomItems = new ArrayList<Item>();
        for (int i = 0; i < maxItems; i++)
            randomItems.add(this.createRandomItem(0, 0));

        var containers = new HashSet<Container>();
        var visContainerMap = new HashMap<Container, List<Item>>();
        for (int i = 0; i < 10; i++) {
            var container = this.createRandomContainer(999, 999);
            containers.add(container);
            visContainerMap.put(new BasicContainer(container), List.of());
        }
        showContainer(visContainerMap, 400);


        var listPacker = new ListPacker(LAFFContainerPacker.class, containers);
        var result = listPacker.pack(randomItems);

        showPacker(result, 0);

        int zOffset = -600;
        for (var e : result.entrySet()) {
            var packer = e.getKey();
            var discarded = listPacker.discarded().get(packer);
            if (discarded != null) {
                this.showPacker(discarded, zOffset);
                zOffset -= 400;
            }
        }

        this.writeHtml();
    }

    /**
     *
     */
    private void showPacker(final Map<Packer, List<Item>> packed, int zOffset) {
        var map = new LinkedHashMap<Container, List<Item>>(packed.size());
        for (var e : packed.entrySet()) {
            map.put(e.getKey().container(), e.getValue());
        }

        this.showContainer(map, zOffset);
    }

    /**
     *
     */
    private void showContainer(final Map<Container, List<Item>> packed, int zOffset) {

        int offset = 0;
        Container lastContainer = null;
        for (var e : packed.entrySet()) {

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