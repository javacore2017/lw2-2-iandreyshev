package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Dat extends Cell<String> {
    @Override
    public String toString() {
        return get();
    }

    @Override
    protected String parse(String valueStr) throws IllegalArgumentException {
        return null;
    }
}
