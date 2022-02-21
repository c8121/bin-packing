package de.c8121.packing;

public enum PackItemResult {
    Success,
    FailedDoesNotFitIn,
    FailedToHeavy,
    FailedNoSpaceLeft,
    FailedReasonUnknown
}
