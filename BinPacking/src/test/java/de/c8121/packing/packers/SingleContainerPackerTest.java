package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.Packer;

class SingleContainerPackerTest {

    /**
     *
     */
    public static void main(String[] args) {

        var container = new Container(20, 20, 20, 100);

        var packer = new SingleContainerPacker(container);

        var item1 = new Item(5, 5, 5, 10);
        var result = packer.add(item1);
        if (Packer.PackItemResult.Success != result)
            throw new RuntimeException("Failed add: " + result);

    }

}