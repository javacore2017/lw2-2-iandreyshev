package ru.iandreyshev.spreadsheetEngine.log;

import ru.iandreyshev.spreadsheetEngine.table.Address;

public class InvalidCellAddressLogEvent extends LogEvent {
    public InvalidCellAddressLogEvent(Address topLeft, Address bottomRight) {
        add(String.format(PATTERN, topLeft, bottomRight));
    }

    private final static String PATTERN =
            "Invalid cell address.\nTop left cell address is %s, bottom right cell address is %s\n";
}
