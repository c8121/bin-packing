package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.Packer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

class SingleContainerPackerTest extends ThreeJsTestBase {

    private SingleContainerPacker packer;

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

        var container = new Container(200, 200, 200, 100);
        this.vis.add(container);

        this.packer = new SingleContainerPacker(container);

        var item = new Item(50, 50, 15, 10);
        this.addItem(item);

        //this.showRemainders(this.packer.state().placement().findPlacement(item));
        //this.vis.copyTo(450, 0, 0);

        for (int i = 0; i < 220; i++) {
            Item randomItem = null;
            try {
                randomItem = this.createRandomItem();
                this.addItem(randomItem);
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to add item " + i + ": " + e.getMessage() + ": " + randomItem);
            }
        }

        //this.showRemainders(this.packer.state().placement().findPlacement(item));
        //this.showAllRemainders(this.packer.state().placement());

        var file = new File(FileUtils.getTempDirectory(), this.getClass().getSimpleName() + ".html");
        System.out.println("Writing to " + file);
        this.vis.writeHtml(file);

    }

    private Item createRandomItem() {
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
    private void addItem(final Item item) {

        var result = this.packer.add(item);
        if (Packer.PackItemResult.Success != result)
            throw new IllegalArgumentException("Failed add: " + result);

        this.vis.add(item);
        this.vis.setStyle(item, "color: " + this.lastColor + ", wireframe: false");
        this.lastColor += 1000;

    }

}