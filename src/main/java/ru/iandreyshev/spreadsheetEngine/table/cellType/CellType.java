package ru.iandreyshev.spreadsheetEngine.table.cellType;

public abstract class CellType<T> {
    private T value;

    public final boolean setFromStr(String str) {
        if (!tryParse(str)) {
            return false;
        }
        value = parse(str);

        return true;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public final T get() {
        return value;
    }

    final void set(T value) {
        this.value = value;
    }

    final T tryCast(Object cellValue) {
        try {
            return (T) cellValue;
        } catch (Exception ex) {
            return null;
        }
    }

    abstract T parse(String str);

    private boolean tryParse(String str) {
        try {
            T val = parse(str);

            if (val == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
