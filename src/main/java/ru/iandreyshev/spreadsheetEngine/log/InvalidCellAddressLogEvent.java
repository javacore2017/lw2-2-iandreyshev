package ru.iandreyshev.spreadsheetEngine.log;

import ru.iandreyshev.spreadsheetEngine.table.Address;

public class InvalidCellAddressLogEvent extends LogEvent {
    public InvalidCellAddressLogEvent(Address topLeft, Address bottomRight) {
        addString(MESSAGE);
        addListItem(FORMAT_HELP);
        addListItem(String.format(SIZE_HELP, topLeft, bottomRight));
    }

    private final static String MESSAGE = "Invalid cell address.";
    private final static String FORMAT_HELP = "Address format is <col as [a-zA-Z]+><row as [0-9]+>";
    private final static String SIZE_HELP = "Top left cell address is %s, bottom right cell address is %s";
}
