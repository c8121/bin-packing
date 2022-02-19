package de.c8121.packing;

public interface Position {

    int x();

    int y();

    int z();

    /**
     *
     */
    static boolean positionEquals(final Position a, final Position b) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        return a.x() == b.x() && a.y() == b.y() && a.z() == b.z();
    }
}
