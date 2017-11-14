package ru.iandreyshev.spreadsheetEngine.table.cellType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Dat extends CellType<String> {
    @Override
    public String toString() {
        return get();
    }

    @Override
    protected String parse(String valueStr) throws IllegalArgumentException {
        return null;
    }
}
