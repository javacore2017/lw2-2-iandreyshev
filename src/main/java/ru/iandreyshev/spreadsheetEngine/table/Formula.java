package ru.iandreyshev.spreadsheetEngine.table;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Formula extends CellType<String> {
    private static final String SIGN_REGEX = "[-+*/]";
    private static final char LEFT_BRACKET = '(';
    private static final char RIGHT_BRACKET = ')';
    private static final String TOKENS_SEPARATOR = " ";

    private List<String> tokens = new ArrayList<>();

    public List<String> getTokens() {
        return tokens;
    }

    @Override
    protected String parse(String str) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(deleteBrackets(str));
        StringBuilder builder = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (!isSign(token) && !isNumber(token) && !Address.tryParse(token)) {
                throw new IllegalArgumentException();
            }
            tokens.add(token);
            builder.append(token);
            builder.append(TOKENS_SEPARATOR);
        }
        String result = builder.toString().trim();

        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    private boolean isSign(String token) {
        Matcher matcher = Pattern.compile(SIGN_REGEX).matcher(token);
        return matcher.matches();
    }

    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private String deleteBrackets(String expression) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); ++i) {
            final char ch = expression.charAt(i);
            result.append((ch != LEFT_BRACKET && ch != RIGHT_BRACKET) ? ch : ' ');
        }
        return result.toString();
    }
}
