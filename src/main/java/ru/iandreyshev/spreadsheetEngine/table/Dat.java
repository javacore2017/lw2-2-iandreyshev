package ru.iandreyshev.spreadsheetEngine.table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Dat extends CellType<String> {
    private static final String REGEX =
            "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";

    @Override
    public String toString() {
        return get();
    }

    @Override
    protected String parse(String valueStr) throws IllegalArgumentException {
        String trimmed = valueStr.trim();
        Matcher matcher = Pattern.compile(REGEX).matcher(trimmed);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid date format");
        }
        return trimmed;
    }
}
