package ru.iandreyshev.spreadsheetEngine.log;

public class ObjectLogEvent extends LogEvent {
    public ObjectLogEvent(Object object) {
        addString(object.toString());
    }
}
