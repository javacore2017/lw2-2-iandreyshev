package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Str extends CellType<String> {
    public Str(String value) throws IllegalArgumentException {
        super(value);
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    protected String parse(String valueStr) throws IllegalArgumentException {
        if (valueStr == null) {
            throw new IllegalArgumentException();
        }
        return valueStr;
    }
}
