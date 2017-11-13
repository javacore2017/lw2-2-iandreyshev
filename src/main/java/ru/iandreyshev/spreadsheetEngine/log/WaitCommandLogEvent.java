package ru.iandreyshev.spreadsheetEngine.log;

public class WaitCommandLogEvent extends LogEvent {
    public WaitCommandLogEvent() {
        addText(MESSAGE);
    }

    private final static String MESSAGE = "Your command: ";
}
