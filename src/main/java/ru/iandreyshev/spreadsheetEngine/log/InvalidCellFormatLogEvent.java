package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidCellFormatLogEvent extends LogEvent {
    public InvalidCellFormatLogEvent() {
        add(MESSAGE);
    }

    private final static String MESSAGE = "Invalid cell format";
}
