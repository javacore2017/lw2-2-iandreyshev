package ru.iandreyshev.spreadsheetEngine.log;

import ru.iandreyshev.spreadsheetEngine.table.Address;

public class StartLogEvent extends LogEvent {
    private final static String START_MESSAGE = "New table created";
    private final static String TABLE_SIZE = "Top left cell address is %s, bottom right cell address is %s";

    public StartLogEvent(Address topLeft, Address bottomRight) {
        addString(START_MESSAGE);
        addListItem(String.format(TABLE_SIZE, topLeft, bottomRight));
    }
}
