package ru.iandreyshev.spreadsheetEngine.table;

final class Str extends CellType<String> {
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
