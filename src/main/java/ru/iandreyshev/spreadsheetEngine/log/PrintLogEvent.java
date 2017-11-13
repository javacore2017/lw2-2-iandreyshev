package ru.iandreyshev.spreadsheetEngine.log;

public class PrintLogEvent extends LogEvent {
    public PrintLogEvent(Object object) {
        addString(object.toString());
    }
}
