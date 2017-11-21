package ru.iandreyshev.spreadsheetEngine.table.cell;

import ru.iandreyshev.spreadsheetEngine.table.Address;
import ru.iandreyshev.spreadsheetEngine.table.Arithmetic;

import java.util.*;

public final class Formula extends Cell<Cell> {
    public static final String UNDEFINED = "%ERROR%";

    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';

    private final List<String> tokens = new ArrayList<>();
    private final HashSet<Address> cells = new HashSet<>();

    public List<String> getTokens() {
        return tokens;
    }

    public HashSet<Address> getCells() {
        return cells;
    }

    public void setResult(Cell value) {
        set(value);
    }

    @Override
    public Cell get() {
        return super.get() == null ? new Str(UNDEFINED) : super.get();
    }

    @Override
    public String toString() {
        return get() == null ? UNDEFINED : get().toString();
    }

    @Override
    protected Cell parse(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(deleteBrackets(str));
        Stack<String> stack = new Stack<>();
        clear();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().toUpperCase();
            Num number = new Num();

            if (!Arithmetic.isSign(token) && !number.setFromStr(token)) {
                if (!Address.tryParse(token)) {
                    return null;
                }
                cells.add(Address.parse(token));
            }
            stack.add(token);
        }
        while (!stack.empty()) {
            tokens.add(stack.pop());
        }
        if (!isNotationValid()) {
            clear();
            return null;
        }

        return new Num();
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

        for (String token : getTokens()) {
            if (!Arithmetic.isSign(token)) {
                stack.add(token);
                continue;
            }
            if (stack.size() < 2) {
                return false;
            }
            stack.pop();
            stack.pop();
            stack.push("");
        }

        return stack.size() == 1;
    }

    private void clear() {
        tokens.clear();
        cells.clear();
    }
}
