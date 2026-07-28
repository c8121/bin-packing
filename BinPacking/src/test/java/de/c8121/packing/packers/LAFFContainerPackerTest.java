package de.c8121.packing.packers;

import de.c8121.packing.Item;
import de.c8121.packing.PackItemResult;
import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LAFFContainerPackerTest {

    @Test
    void oneContainerTest() {

        var container = new BasicContainer(200, 200, 200, 400);

        var items = new ArrayList<Item>();
        items.add(new BasicItem(30, 60, 40, 10));
        items.add(new BasicItem(90, 90, 90, 20));

        var packer = new LAFFContainerPacker(container);
        var result = packer.pack(items);

        assertEquals(0, result.getFailed().size());
        assertEquals(2, result.get(PackItemResult.Success).size());
    }

}