package ru.iandreyshev.spreadsheetEngine.log;

public class InvalidCommandLogEvent extends LogEvent {
    public InvalidCommandLogEvent() {
        addString(MESSAGE);
        addListItem(HELP);
    }

    private final static String MESSAGE = "Invalid command format.";
    private final static String HELP = "Use 'help' to see all available commands.";
}
