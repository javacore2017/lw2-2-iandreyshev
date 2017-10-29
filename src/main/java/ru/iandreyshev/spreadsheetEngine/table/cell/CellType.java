package ru.iandreyshev.spreadsheetEngine.table.cell;

public abstract class CellType<T> {
    public CellType(T value) {
        this.value = value;
    }

    public final T get() {
        return value;
    }

    public final void set(T value) {
        this.value = value;
    }

    public Int intValue() throws ClassCastException {
        throw new ClassCastException("Invalid cell type cast");
    }

    public Str strValue() throws ClassCastException {
        throw new ClassCastException("Invalid cell type cast");
    }

    public Dat datValue() throws ClassCastException {
        throw new ClassCastException("Invalid cell type cast");
    }

    public void add(CellType value) {
        throw new IllegalArgumentException();
    }

    public void sub(CellType value) {
        throw new IllegalArgumentException();
    }

    public void mul(CellType value) {
        throw new IllegalArgumentException();
    }

    public void div(CellType value) {
        throw new IllegalArgumentException();
    }

    public abstract String toString();

    private T value;
}
