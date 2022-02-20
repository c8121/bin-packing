package de.c8121.packing;

public interface Packer {

    enum PackItemResult {
        Success,
        FailedDoesNotFitIn,
        FailedToHeavy,
        FailedNoSpaceLeft,
        FailedReasonUnknown
    }

    /**
     * Try to add given {@link Item} to given {@link Container}.
     */
    PackItemResult add(final Item item);
}
