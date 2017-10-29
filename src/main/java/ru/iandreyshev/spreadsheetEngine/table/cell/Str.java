package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Str extends CellType<String> {
    public Str(String value) {
        super(value);
    }

    @Override
    public Str strValue() {
        return this;
    }

    @Override
    public String toString() {
        return get();
    }
}
