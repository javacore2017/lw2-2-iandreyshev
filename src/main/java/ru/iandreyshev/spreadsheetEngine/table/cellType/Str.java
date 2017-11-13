package ru.iandreyshev.spreadsheetEngine.table.cellType;

public final class Str extends CellType<String> {
    public Str() {
        set("");
    }

    public Str(String value) {
        set(value);
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
