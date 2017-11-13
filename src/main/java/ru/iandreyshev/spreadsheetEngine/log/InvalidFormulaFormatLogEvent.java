package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidFormulaFormatLogEvent extends LogEvent {
    private final static String MESSAGE = "Invalid expression '%s' for formula format";

    public InvalidFormulaFormatLogEvent(String expression) {
        addString(String.format(MESSAGE, expression));
    }
}
