package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidCellFormatLogEvent extends LogEvent {
    private final static String MESSAGE = "Invalid cell format";

    public InvalidCellFormatLogEvent() {
        addString(MESSAGE);
    }
}
