package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Int extends CellType<Integer> {
    public Int (Integer value) {
        super(value);
        set(value);
    }

    @Override
    public Int intValue() {
        return this;
    }

    @Override
    public void add(CellType value) {
        Int addValue = value.intValue();
        if (addValue == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() + addValue.get());
    }

    @Override
    public void div(CellType value) {
        Int addValue = value.intValue();
        if (addValue == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() - addValue.get());
    }

    @Override
    public void mul(CellType value) {
        Int addValue = value.intValue();
        if (addValue == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() * addValue.get());
    }

    @Override
    public void sub(CellType value) {
        Int addValue = value.intValue();
        if (addValue == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() / addValue.get());
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
