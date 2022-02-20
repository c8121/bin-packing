package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.Packer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SingleContainerPackerTest extends ThreeJsTestBase {

    private long lastColor = 0xff0000;

    /**
     *
     */
    public static void main(String[] args) throws IOException {
        new SingleContainerPackerTest().test();
    }

    /**
     *
     */
    public void test() throws IOException {

        var container = new Container(200, 200, 200, 400);
        this.vis.add(container);

        var packer = new SingleContainerPacker(container);

        var item = new Item(50, 50, 15, 10);
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

    /**
     *
     */
    protected void packContainer(final Packer packer, final List<Item> items) {

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
    protected Item createRandomItem() {
        var random = ThreadLocalRandom.current();
        return new Item(
                random.nextInt(10, 90),
                random.nextInt(10, 90),
                random.nextInt(10, 90),
                random.nextInt(1, 10)
        );
    }

    /**
     *
     */
    protected Packer.PackItemResult addItem(final Packer packer, final Item item) {

        var result = packer.add(item);
        if (Packer.PackItemResult.Success != result)
            return result;

        this.vis.add(item);
        this.vis.setStyle(item, "color: " + this.lastColor + ", wireframe: false");
        this.lastColor += 1000;

        return result;
    }

}