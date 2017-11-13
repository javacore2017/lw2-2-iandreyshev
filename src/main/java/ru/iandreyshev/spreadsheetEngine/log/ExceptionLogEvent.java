package ru.iandreyshev.spreadsheetEngine.log;

public class ExceptionLogEvent extends LogEvent {
    public ExceptionLogEvent(Exception ex) {
        addString(String.format(PATTERN, ex.getMessage()));
    }

    private final static String PATTERN = "Catch exception: %s";
}
