package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.PackItemResult;
import de.c8121.packing.Packer;
import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Base implementation for testing container packaging.
 */
public abstract class ContainerPackerTestBase extends ThreeJsTestBase {

    private long lastColor = 0xff0000;

    /**
     *
     */
    public Item createRandomItem() {
        var random = ThreadLocalRandom.current();
        return new BasicItem(
                random.nextInt(10, 90),
                random.nextInt(10, 90),
                random.nextInt(10, 90),
                random.nextInt(1, 10)
        );
    }

    /**
     *
     */
    public Container createRandomContainer() {
        var random = ThreadLocalRandom.current();
        return new BasicContainer(
                random.nextInt(100, 400),
                random.nextInt(100, 400),
                random.nextInt(100, 400),
                random.nextInt(350, 400)
        );
    }

    /**
     *
     */
    public void packList(final Packer packer, final List<Item> items) {

        var result = packer.pack(items);

        var success = result.get(PackItemResult.Success);
        for (var i : success) {
            this.vis.add(i);
            this.vis.setStyle(i, "color: " + this.lastColor + ", wireframe: false");
            this.lastColor += 1000;
        }

        System.out.println(packer);
        System.out.println(result);
    }

    /**
     *
     */
    public PackItemResult addItem(final Packer packer, final Item item) {

        var result = packer.add(item);
        if (PackItemResult.Success != result)
            return result;

        this.vis.add(item);
        this.vis.setStyle(item, "color: " + this.lastColor + ", wireframe: false");
        this.lastColor += 1000;

        return result;
    }

}