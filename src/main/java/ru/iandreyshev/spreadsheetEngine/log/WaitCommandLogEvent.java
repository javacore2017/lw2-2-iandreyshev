package ru.iandreyshev.spreadsheetEngine.log;

public class WaitCommandLogEvent extends LogEvent {
    private final static String MESSAGE = "Your command: ";

    public WaitCommandLogEvent() {
        addText(MESSAGE);
    }
}
