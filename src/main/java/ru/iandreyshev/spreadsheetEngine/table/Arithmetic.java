package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cellType.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Arithmetic {
    private static final String SUM_SIGN = "+";
    private static final String SUB_SIGN = "-";
    private static final String MUL_SIGN = "*";
    private static final String DIV_SIGN = "/";
    private static final String SIGN_REGEX = "[-+*/]";

    public static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    static CellType calc(CellType first, CellType second, String sign) {
        if (sign.compareTo(SUM_SIGN) == 0) {
            return sum(first, second);
        }
        if (sign.compareTo(SUB_SIGN) == 0) {
            return sub(first, second);
        }
        if (sign.compareTo(MUL_SIGN) == 0) {
            return mul(first, second);
        }
        if (sign.compareTo(DIV_SIGN) == 0) {
            return div(first, second);
        }
        return new Str("Null");
    }

    public static boolean isSign(String token) {
        final Matcher matcher = Pattern.compile(SIGN_REGEX).matcher(token);

        return matcher.matches();
    }

    private static CellType sum(CellType left, CellType right) {
        if (isInt(left) && isInt(right)) {
            int result = ((Int) left).get() + ((Int) right).get();
            return new Int(result);
        }

        return null;
    }

    private static CellType sub(CellType left, CellType right) {
        if (isInt(left) && isInt(right)) {
            int result = ((Int) left).get() - ((Int) right).get();
            return new Int(result);
        }

        return null;
    }

    private static CellType mul(CellType left, CellType right) {
        if (isInt(left) && isInt(right)) {
            int result = ((Int) left).get() * ((Int) right).get();
            return new Int(result);
        }

        return null;
    }

    private static CellType div(CellType left, CellType right) throws IllegalArgumentException {
        if (isInt(left) && isInt(right)) {
            if (((Int) right).get() == 0) {
                throw new IllegalArgumentException("Divide by zero");
            }
            int result = ((Int) left).get() / ((Int) right).get();
            return new Int(result);
        }

        return null;
    }

    private static boolean isInt(CellType value) {
        return value instanceof Int;
    }
}
