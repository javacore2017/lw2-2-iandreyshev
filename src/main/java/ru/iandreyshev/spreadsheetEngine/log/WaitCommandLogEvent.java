package ru.iandreyshev.spreadsheetEngine.log;

public class WaitCommandLogEvent extends LogEvent {
    public WaitCommandLogEvent() {
        add(MESSAGE);
    }

    private final static String MESSAGE = "Your command: ";
}
