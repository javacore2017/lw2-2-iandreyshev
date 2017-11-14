package ru.iandreyshev.spreadsheetEngine.table.cellType;

import ru.iandreyshev.spreadsheetEngine.table.Address;
import ru.iandreyshev.spreadsheetEngine.table.Arithmetic;

import java.util.*;

public final class Formula extends CellType<CellType> {
    public static final String UNDEFINED = "Undefined";

    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';

    private List<String> tokens = new ArrayList<>();
    private HashSet<Address> cells = new HashSet<>();
    private HashSet<String> cellsTokens = new HashSet<>();

    public List<String> getTokens() {
        return tokens;
    }

    public HashSet<Address> getCells() {
        return cells;
    }

    public HashSet<String> getCellsTokens() { return cellsTokens; }

    public void setResult(CellType value) {
        set(value);
    }

    @Override
    public CellType get() {
        CellType value = super.get();
        return value == null ? new Str(UNDEFINED) : value;
    }

    @Override
    public String toString() {
        CellType value = get();

        return value == null ? UNDEFINED : value.toString();
    }

    @Override
    protected CellType parse(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(deleteBrackets(str));
        tokens.clear();
        cells.clear();
        cellsTokens.clear();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toUpperCase();

            if (!Arithmetic.isSign(token) && !Arithmetic.isInt(token)) {
                if (Address.tryParse(token)) {
                    cells.add(Address.parse(token));
                    cellsTokens.add(token);
                } else {
                    return null;
                }
            }
            tokens.add(token);
        }
        if (!isNotationValid()) {
            return null;
        }

        return new Int();
    }

    private String deleteBrackets(String expression) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); ++i) {
            final char ch = expression.charAt(i);
            result.append((ch != LEFT_BRACKET && ch != RIGHT_BRACKET) ? ch : ' ');
        }

        return result.toString();
    }

    private boolean isNotationValid() {
        Stack<String> stack = new Stack<>();
        stack.addAll(getTokens());

        while (stack.size() > 2) {
            String second = stack.pop();
            String first = stack.pop();
            String sign = stack.pop();

            if (!Arithmetic.isSign(sign)) {
                return false;
            } else if (!Arithmetic.isInt(first) && !Address.tryParse(first)) {
                return false;
            } else if (!Arithmetic.isInt(second) && !Address.tryParse(second)) {
                return false;
            }
            stack.add("0");
        }

        return stack.size() == 1 && (Arithmetic.isInt(stack.peek()) || Address.tryParse(stack.peek()));
    }
}
