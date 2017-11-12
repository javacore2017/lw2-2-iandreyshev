package ru.iandreyshev.spreadsheetEngine.log;

public class ExceptionLogEvent extends LogEvent {
    public ExceptionLogEvent(Exception ex) {
        add(String.format(PATTERN, ex.getMessage()));
    }

    private final static String PATTERN = "Catch exception: %s\n";
}
