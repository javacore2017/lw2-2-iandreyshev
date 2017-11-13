package ru.iandreyshev.spreadsheetEngine.table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
    private static final String ADDRESS_REGEX = "( *)([a-zA-Z]+)([0-9]+)( *)";
    private static final Integer ADDRESS_RADIX = 26;
    private static final int COL_GROUP = 2;
    private static final int ROW_GROUP = 3;
    private static final int MIN_VALUE = 1;

    private int col;
    private int row;

    public static Address parse(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Address is null");
        }
        final Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid cell address format");
        }

        try {
            final int col = colToNumber(matcher.group(COL_GROUP));
            final int row = Integer.parseUnsignedInt(matcher.group(ROW_GROUP));
            return new Address(row, col);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cell address format");
        }
    }

    public static boolean tryParse(String str) {
        try {
            parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String colToString(int col) throws IllegalArgumentException {
        if (col < MIN_VALUE) {
            throw new IllegalArgumentException("Invalid column value");
        }

        StringBuilder resultStr = new StringBuilder();
        final String str = Integer.toString(col, ADDRESS_RADIX + 1);

        for (int i = 0; i < str.length(); ++i) {
            final char ch = str.charAt(i);
            final String chStr = (new StringBuilder()).append(ch).toString();
            final int offset = Integer.parseInt(chStr, ADDRESS_RADIX + 1);
            final int code = (i == 0) ? 'A' + offset - 1 : 'A' + offset;
            resultStr.append(Character.toChars(code)[0]);
        }
        return resultStr.toString();
    }

    public static int colToNumber(String col) throws IllegalArgumentException {
        if (col == null || col.isEmpty()) {
            throw new IllegalArgumentException("Invalid column format");
        }
        StringBuilder resultStr = new StringBuilder();

        for (int i = 0; i < col.length(); ++i) {
            if (Character.isDigit(col.charAt(i))) {
                throw new IllegalArgumentException("Invalid column format");
            }
            char ch = Character.toLowerCase(col.charAt(i));

            if (ch >= 'a' && ch <= 'i') {
                final int offset = ch - 'a';
                ch = Character.toChars('1' + offset)[0];
            } else {
                ch = Character.toChars('a' + ch - 'i' - 1)[0];
            }
            resultStr.append(ch);
        }
        return Integer.parseUnsignedInt(resultStr.toString(), ADDRESS_RADIX);
    }

    public Address(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public String getColStr() {
        return colToString(getCol());
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return getColStr() + row;
    }
}
