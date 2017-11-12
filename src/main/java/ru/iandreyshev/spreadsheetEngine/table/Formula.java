package ru.iandreyshev.spreadsheetEngine.table;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Formula extends CellType<String> {
    public Integer getResult() {
        return result;
    }

    @Override
    public String toString() {
        return (get() == null) ? NOT_CALC_VALUE : result.toString();
    }

    @Override
    protected String parse(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(prepareToParse(str));
        StringBuilder builder = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!isSignToken(token) && !Address.tryParse(token)) {
                throw new IllegalArgumentException();
            }
            builder.append(tokenizer.nextToken());
            builder.append(" ");
        }

        String result = builder.toString().trim();
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    private static final String SIGN_REGEX = "( |\\-|\\+|\\*|\\/)+";
    private static final String NOT_CALC_VALUE = "Null";

    private Integer result = null;

    private boolean isSignToken(String token) {
        Matcher matcher = Pattern.compile(SIGN_REGEX).matcher(token);
        return matcher.matches();
    }

    private String prepareToParse(String expression) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expression.length(); ++i) {
            if (expression.charAt(i) != '(' && expression.charAt(i) != ')') {
                result.append(expression.charAt(i));
            }
        }
        return result.toString();
    }
}
