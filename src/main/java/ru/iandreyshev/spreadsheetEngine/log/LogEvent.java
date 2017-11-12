package ru.iandreyshev.spreadsheetEngine.log;

public class LogEvent {
    @Override
    public String toString() {
        return message;
    }

    LogEvent() { }

    void add(String str) {
        message += str;
    }

    private String message = OUTPUT_PATTERN;

    private final static String OUTPUT_PATTERN = "> ";
}
