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
     * <p>
     * <b>Note</b>: A Packer implementation might require sorting of items to
     * achieve best results.
     */
    PackItemResult add(final Item item);
}
