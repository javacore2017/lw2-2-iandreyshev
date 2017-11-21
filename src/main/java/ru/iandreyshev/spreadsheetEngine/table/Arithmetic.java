package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cell.*;

import java.util.regex.Pattern;

public final class Arithmetic {
    private static final String SUM_SIGN = "+";
    private static final String SUB_SIGN = "-";
    private static final String MUL_SIGN = "*";
    private static final String DIV_SIGN = "/";
    private static final String SIGN_REGEX = "[-+*/]";
    private static final Pattern SIGN_PATTERN = Pattern.compile(SIGN_REGEX);

    static Cell calc(Cell first, Cell second, String sign) {
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
        return new Str(Formula.UNDEFINED);
    }

    public static boolean isSign(String token) {
        return SIGN_PATTERN.matcher(token).matches();
    }

    private static Cell sum(Cell left, Cell right) {
        if (isNum(left) && isNum(right)) {
            float result = ((Num) left).get() + ((Num) right).get();
            return new Num(result);
        }

        return null;
    }

    private static Cell sub(Cell left, Cell right) {
        if (isNum(left) && isNum(right)) {
            float result = ((Num) left).get() - ((Num) right).get();
            return new Num(result);
        }

        return null;
    }

    private static Cell mul(Cell left, Cell right) {
        if (isNum(left) && isNum(right)) {
            float result = ((Num) left).get() * ((Num) right).get();
            return new Num(result);
        }

        return null;
    }

    private static Cell div(Cell left, Cell right) throws IllegalArgumentException {
        if (isNum(left) && isNum(right)) {
            if (((Num) right).get() == 0) {
                throw new IllegalArgumentException("Divide by zero");
            }
            float result = ((Num) left).get() / ((Num) right).get();
            return new Num(result);
        }

        return null;
    }

    private static boolean isNum(Cell value) {
        return value instanceof Num;
    }
}
