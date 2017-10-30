package ru.iandreyshev.spreadsheetEngine.table.cell;

import java.util.StringTokenizer;

public class Formula extends CellType<Integer> {
    public Formula(String formula) throws IllegalArgumentException {
        super(formula);
        StringBuilder withoutBrackets = new StringBuilder();
        for (int i = 0; i < formula.length(); ++i) {
            if (formula.charAt(i) != '(' && formula.charAt(i) != ')') {
                withoutBrackets.append(formula.charAt(i));
            }
        }

        StringTokenizer tokenizer = new StringTokenizer(withoutBrackets.toString());
        StringBuilder result = new StringBuilder(tokenizer.nextToken());

        while (tokenizer.hasMoreTokens()) {
            result.append(" ");
            result.append(tokenizer.nextToken());
        }

        tokensForm = result.toString();
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @Override
    protected Integer parse(String valueStr) throws IllegalArgumentException {
        return null;
    }

    private String tokensForm;
}
