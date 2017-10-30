package ru.iandreyshev.spreadsheetEngine.table.cell;

public abstract class CellType<T> {
    CellType(String valueStr) throws IllegalArgumentException {
        try {
            set(parse(valueStr));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Can not create cell type object from string " + valueStr);
        }
    }

    public final T get() {
        return value;
    }

    public final void set(T value) {
        this.value = value;
    }

    public void add(CellType value) {}

    public void sub(CellType value) {}

    public void mul(CellType value) {}

    public void div(CellType value) {}

    public abstract String toString();

    protected abstract T parse(String valueStr) throws IllegalArgumentException;

    private T value;
}
