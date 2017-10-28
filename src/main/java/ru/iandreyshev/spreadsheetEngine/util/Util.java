package ru.iandreyshev.spreadsheetEngine.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class Util {
    public static Vec2i toAddress(String address) {
        Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(address);

        if (!matcher.matches()) {
            return null;
        }

        int col;
        int row;

        try {
            col = Integer.parseUnsignedInt(matcher.group(ADDRESS_COL_GROUP));
            row = Integer.parseUnsignedInt(matcher.group(ADDRESS_ROW_GROUP), TABLE_ADDRESS_RADIX);
        } catch (Exception e) {
            return null;
        }

        return new Vec2i(col, row);
    }

    private static final int TABLE_ADDRESS_RADIX = 26;
    private static final String ADDRESS_REGEX = "( *)([0-9]+)([a-zA-Z]+)( *)";
    private static final int ADDRESS_COL_GROUP = 2;
    private static final int ADDRESS_ROW_GROUP = 3;
}
