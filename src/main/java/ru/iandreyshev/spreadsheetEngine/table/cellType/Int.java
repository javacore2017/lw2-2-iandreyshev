package ru.iandreyshev.spreadsheetEngine.table.cellType;

public final class Int extends CellType<Integer> {
    public Int() {
        set(0);
    }

    public Int(int value) {
        set(value);
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @Override
    protected Integer parse(String str) throws IllegalArgumentException {
        return Integer.parseInt(str.trim());
    }
}
