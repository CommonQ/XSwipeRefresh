package io.github.commonq.lib;


public enum SwipyRefreshLayoutDirection {

    TOP(0),
    BOTTOM(1),
    BOTH(2),
    NONE(3);

    private int mValue;

    SwipyRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipyRefreshLayoutDirection getFromInt(int value) {
        for (SwipyRefreshLayoutDirection direction : SwipyRefreshLayoutDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
