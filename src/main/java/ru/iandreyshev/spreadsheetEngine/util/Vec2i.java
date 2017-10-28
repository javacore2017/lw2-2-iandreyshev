package ru.iandreyshev.spreadsheetEngine.util;

public class Vec2i {
    public static Vec2i zero() {
        return new Vec2i(0, 0);
    };

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y +"]";
    }

    private int x = 0;
    private int y = 0;
}
