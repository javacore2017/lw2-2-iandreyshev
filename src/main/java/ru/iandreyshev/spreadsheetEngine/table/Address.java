package ru.iandreyshev.spreadsheetEngine.table;

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

    public static String colToString(int col) throws IllegalArgumentException {
        if (col < MIN_VALUE) {
            throw new IllegalArgumentException("Try convert invalid col value");
        }

        StringBuilder resultStr = new StringBuilder();

        if (col <= ADDRESS_RADIX) {
            resultStr.append(Character.toChars('A' + col - 1)[0]);
        } else {
            String str = Integer.toString(col, ADDRESS_RADIX);

            for (int i = 0; i < str.length(); ++i) {
                char ch = Character.toLowerCase(str.charAt(i));


            }
        }

        return resultStr.toString();
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getColStr() {
        return colToString(getCol());
    }

    @Override
    public String toString() {
        return row + getColStr();
    }

    private static final String ADDRESS_REGEX = "( *)([0-9]+)([a-zA-Z]+)( *)";
    private static final Integer ADDRESS_RADIX = 26;
    private static final int ROW_GROUP = 2;
    private static final int COL_GROUP = 3;
    private static final int MIN_VALUE = 1;

    private int row;
    private int col;
}
