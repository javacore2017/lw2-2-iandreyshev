package ru.iandreyshev.spreadsheetEngine.table.cell;

import java.util.Date;

public class Dat extends CellType<Long> {
    public Dat(String date) throws IllegalArgumentException {
        super(0L);
        try {
            assignDate(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    public Dat(Long unixDate) {
        super(0L);
    }

    @Override
    public String toString() {
        return new Date(get()).toString();
    }

    private void assignDate(String date) {
    }
}
