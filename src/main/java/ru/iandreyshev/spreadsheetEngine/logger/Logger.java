package ru.iandreyshev.spreadsheetEngine.logger;

import ru.iandreyshev.spreadsheetEngine.table.util.Address;

final public class Logger {
    public static void start() {
        System.out.print(START_MESSAGE);
    }

    public static void print(Object output) {
        String str = (output == null) ? NULL : output.toString();
        System.out.printf(OUTPUT_PATTERN, str);
    }

    public static void onSet(Address address, String valueStr) {
    }

    public static void waitCmd() {
        System.out.print(WAITING_COMMAND);
    }

    public static void invalidCommand() {
        print(INVALID_COMMAND_FORMAT);
    }

    public static void invalidCellFormat() {
        print(INVALID_CELL_FORMAT);
    }

    public static void invalidCellAddress(int maxRow, int maxCol) {
        System.out.printf(INVALID_CELL_ADDRESS, maxRow, maxCol);
    }

    public static void help() {
        print(HELP_MESSAGE);
    }

    public static void exception(String message) {
        System.out.printf(EXCEPTION_PATTERN, message);
    }

    private final static String START_MESSAGE = "New table created\n";
    private final static String OUTPUT_PATTERN = "> %s\n";
    private final static String WAITING_COMMAND = "> Your command: ";
    private final static String NULL = "null";
    private final static String INVALID_COMMAND_FORMAT =
            "Invalid command format. Use 'help' to see all available commands";
    private final static String INVALID_CELL_FORMAT = "Invalid cell format";
    private final static String INVALID_CELL_ADDRESS =
            "> Invalid cell address. Max row: %d, max col: %d\n";
    private final static String EXCEPTION_PATTERN = "Catch exception: %s\n";
    private final static String HELP_MESSAGE =
            "Available commands:\n" +
                    "   GET <cell>                      to get cell value,\n" +
                    "   SET <cell> :<value>            to set cell value,\n" +
                    "   SETFORMULA <cell> :<formula>   to set formula into cell,\n" +
                    "   DISPLAY                         to display table,\n" +
                    "   HELP                            to write all available commands,\n" +
                    "   EXIT                            to exit from application.\n";
}
