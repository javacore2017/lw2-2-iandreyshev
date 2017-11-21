package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidFormulaFormatLogEvent extends LogEvent {
    private final static String MESSAGE = "Illegal expression '%s'. Error: %s.";

    public InvalidFormulaFormatLogEvent(String formula, String errorMessage) {
        addString(String.format(MESSAGE, formula, errorMessage));
    }
}
