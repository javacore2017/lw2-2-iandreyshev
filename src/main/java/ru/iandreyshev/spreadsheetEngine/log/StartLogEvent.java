package ru.iandreyshev.spreadsheetEngine.log;

import ru.iandreyshev.spreadsheetEngine.table.Address;

public class StartLogEvent extends LogEvent {
    public StartLogEvent(Address topLeft, Address bottomRight) {
        add(START_MESSAGE);
        add(String.format(TABLE_SIZE, topLeft, bottomRight));
    }

    private final static String START_MESSAGE = "New table created\n";
    private final static String TABLE_SIZE = "Top left cell address is %s, bottom right cell address is %s\n";
}
