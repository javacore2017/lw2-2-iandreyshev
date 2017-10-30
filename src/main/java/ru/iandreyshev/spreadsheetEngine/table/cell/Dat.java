package ru.iandreyshev.spreadsheetEngine.table.cell;

import java.util.Date;

public class Dat extends CellType<Long> {
    public Dat(String date) throws IllegalArgumentException {
        super(date);
    }

    @Override
    public String toString() {
        return new Date(get()).toString();
    }

    @Override
    protected Long parse(String valueStr) throws IllegalArgumentException {
        return Long.parseLong(valueStr);
    }
}
