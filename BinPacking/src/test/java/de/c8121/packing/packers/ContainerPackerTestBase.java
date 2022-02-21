package de.c8121.packing.packers;

import de.c8121.packing.Item;
import de.c8121.packing.Packer;
import de.c8121.packing.util.BasicItem;

import java.util.HashMap;
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
    public void packContainer(final Packer packer, final List<Item> items) {

        var resultInfo = new HashMap<Packer.PackItemResult, Integer>();
        int packedVolume = 0;
        int packedWeight = 0;

        for (var item : items) {

            var result = this.addItem(packer, item);
            var cnt = resultInfo.get(result);
            resultInfo.put(result, cnt != null ? cnt + 1 : 1);

            if (result == Packer.PackItemResult.Success) {
                packedVolume += item.xs() * item.ys() * item.zs();
                packedWeight += item.weight();
            }
        }

        System.out.println(packer);
        System.out.println(resultInfo);
        System.out.println("Packed volume=" + packedVolume + ", weight=" + packedWeight);
    }

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
    public Packer.PackItemResult addItem(final Packer packer, final Item item) {

        var result = packer.add(item);
        if (Packer.PackItemResult.Success != result)
            return result;

        this.vis.add(item);
        this.vis.setStyle(item, "color: " + this.lastColor + ", wireframe: false");
        this.lastColor += 1000;

        return result;
    }

}