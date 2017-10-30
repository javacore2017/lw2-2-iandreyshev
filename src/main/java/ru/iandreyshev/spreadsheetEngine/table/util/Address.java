package ru.iandreyshev.spreadsheetEngine.table.util;

import ru.iandreyshev.spreadsheetEngine.table.cell.Str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
    public static Address parse(String str) throws IllegalArgumentException {
        if (str == null) {
            throw new IllegalArgumentException("Address is null");
        }
        Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(str);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid cell address format");
        }

        int row;
        int col;

        try {
            row = Integer.parseUnsignedInt(matcher.group(ROW_GROUP)) - 1;
            col = colToNumber(matcher.group(COL_GROUP)) - 1;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cell address format");
        }

        return new Address(row, col);
    }
    public static boolean tryParse(String str) {
        try {
            parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static String colToString(Integer col) throws IllegalArgumentException {
        if (col < 0) {
            throw new IllegalArgumentException("Try convert invalid col value");
        }
        return "";
    }
    public static int colToNumber(String col) throws IllegalArgumentException {
        if (col == null || col.isEmpty()) {
            throw new IllegalArgumentException("Invalid col format");
        }

        StringBuilder resultStr = new StringBuilder();

        for (int i = 0; i < col.length(); ++i) {
            char ch = col.charAt(i);
            if (!Character.isAlphabetic(ch)) {
                throw new IllegalArgumentException("Invalid col format");
            }
            ch = Character.toLowerCase(ch);
            if (ch >= 'a' && ch <= 'i') {
                int offset = ch - 'a';
                ch = Character.toChars('1' + offset)[0];
            } else {
                ch = Character.toChars('a' + ch - 'i' - 1)[0];
            }
            resultStr.append(ch);
        }

        return Integer.parseUnsignedInt(resultStr.toString(), ADDRESS_RADIX);
    }

    public Address(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col +"]";
    }

    private static final String ADDRESS_REGEX = "( *)([0-9]+)([a-zA-Z]+)( *)";
    private static final Integer ADDRESS_RADIX = 26;
    private static final int ROW_GROUP = 2;
    private static final int COL_GROUP = 3;

    private int row;
    private int col;
}
