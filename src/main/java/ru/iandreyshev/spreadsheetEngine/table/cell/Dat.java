package ru.iandreyshev.spreadsheetEngine.table.cell;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Dat extends Cell<Long> {
    private static final String PATTERN = "yyyy-MM-dd";

    @Override
    public String toString() {
        return new SimpleDateFormat(PATTERN).format(get());
    }

    @Override
    protected Long parse(String valueStr) throws IllegalArgumentException {
        try {
            Date date = new SimpleDateFormat(PATTERN).parse(valueStr);
            return date.getTime();
        } catch (Exception ex) {
            return null;
        }
    }
}
