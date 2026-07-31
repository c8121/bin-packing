package de.c8121.packing.packers;

import de.c8121.packing.Container;
import de.c8121.packing.Item;
import de.c8121.packing.PackItemResult;
import de.c8121.packing.util.BasicContainer;
import de.c8121.packing.util.BasicItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LAFFContainerPackerTest {

    /**
     * Packing one single container
     */
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

    /**
     * One item bigger than available container:
     * {@link PackItemResult#FailedDoesNotFitIn}
     */
    @Test
    void testItemNotFittingBySize() {
        var container = new BasicContainer(200, 200, 200, 400);

        var items = new ArrayList<Item>();
        items.add(new BasicItem(300, 60, 40, 10));
        items.add(new BasicItem(90, 90, 90, 20));

        var packer = new LAFFContainerPacker(container);
        var result = packer.pack(items);

        assertEquals(1, result.getFailed().size());
        assertEquals(1, result.get(PackItemResult.Success).size());
        assertEquals(1, result.get(PackItemResult.FailedDoesNotFitIn).size());
    }

    /**
     * One item heavier than container max capacity:
     * {@link PackItemResult#FailedToHeavy}
     */
    @Test
    void testItemNotFittingByWeight() {
        var container = new BasicContainer(200, 200, 200, 400);

        var items = new ArrayList<Item>();
        items.add(new BasicItem(100, 60, 40, 500));
        items.add(new BasicItem(90, 90, 90, 20));

        var packer = new LAFFContainerPacker(container);
        var result = packer.pack(items);

        assertEquals(1, result.getFailed().size());
        assertEquals(1, result.get(PackItemResult.Success).size());
        assertEquals(1, result.get(PackItemResult.FailedToHeavy).size());
    }

    /**
     * Not enough space in container for all items:
     * {@link PackItemResult#FailedNoSpaceLeft}
     */
    @Test
    void testNoSpaceLeftInContainer() {
        var container = new BasicContainer(200, 200, 200, 400);

        var items = new ArrayList<Item>();
        items.add(new BasicItem(190, 190, 190, 20));
        items.add(new BasicItem(90, 90, 90, 20));

        var packer = new LAFFContainerPacker(container);
        var result = packer.pack(items);

        assertEquals(1, result.getFailed().size());
        assertEquals(1, result.get(PackItemResult.Success).size());
        assertEquals(1, result.get(PackItemResult.FailedNoSpaceLeft).size());
    }

    /**
     * Packing multiple containers
     */
    @Test
    void multipleContainersTest() throws ReflectiveOperationException {

        var containers = new HashSet<Container>();
        containers.add(new BasicContainer(100, 200, 200, 400));
        containers.add(new BasicContainer(250, 200, 200, 400));

        var items = new ArrayList<Item>();
        items.add(new BasicItem(130, 60, 40, 100));
        items.add(new BasicItem(190, 90, 90, 200));
        items.add(new BasicItem(130, 50, 40, 100));
        items.add(new BasicItem(190, 80, 90, 200));
        items.add(new BasicItem(90, 80, 90, 200));


        var listPacker = new ListPacker(LAFFContainerPacker.class, containers);
        var result = listPacker.pack(items);

        assertEquals(2, result.size()); //Two containers because of weight

        int packedItems = 0;
        for (var e : result.entrySet()) {
            var containerItems = e.getValue();
            packedItems += containerItems.size();
        }
        assertEquals(items.size(), packedItems);
    }
}