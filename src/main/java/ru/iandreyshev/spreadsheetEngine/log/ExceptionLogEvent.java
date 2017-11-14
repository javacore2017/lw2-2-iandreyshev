package ru.iandreyshev.spreadsheetEngine.log;

public class ExceptionLogEvent extends LogEvent {
    private final static String PATTERN = "Catch exception: %s";

    public ExceptionLogEvent(Exception ex) {
        addString("\n");
        addString(String.format(PATTERN, ex.getMessage()));
    }
}
