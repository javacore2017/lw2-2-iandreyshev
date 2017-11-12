package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidCommandLogEvent extends LogEvent {
    public InvalidCommandLogEvent() {
        add(MESSAGE);
    }

    private final static String MESSAGE = "Invalid command format. Use 'help' to see all available commands";
}
