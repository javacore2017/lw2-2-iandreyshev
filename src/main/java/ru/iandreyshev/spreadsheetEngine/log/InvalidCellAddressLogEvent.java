package ru.iandreyshev.spreadsheetEngine.log;

import ru.iandreyshev.spreadsheetEngine.table.Address;

public class InvalidCellAddressLogEvent extends LogEvent {
    private final static String MESSAGE = "Invalid cell address.";
    private final static String SIZE_HELP = "Top left cell address is %s, bottom right cell address is %s";

    public InvalidCellAddressLogEvent(Address topLeft, Address bottomRight) {
        addString(MESSAGE);
        addListItem(String.format(SIZE_HELP, topLeft, bottomRight));
    }
}
