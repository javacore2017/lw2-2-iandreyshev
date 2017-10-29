package ru.iandreyshev.spreadsheetEngine.util;

final public class Logger {
    public static void start() {
        System.out.print(START_MESSAGE);
    }

    public static void output(Object output) {
        String str = (output == null) ? NULL : output.toString();
        System.out.printf(OUTPUT_PATTERN, str);
    }

    public static void invalidCommand() {
        System.out.println(INVALID_COMMAND_MESSAGE);
    }

    public static void invalidCellFormat() {
        System.out.println(INVALID_CELL_FORMAT);
    }

    public static void invalidCellAddress() {
        System.out.println(INVALUD_CELL_ADDRESS);
    }

    public static void help() {
        System.out.print(HELP_MESSAGE);
    }

    private final static String START_MESSAGE = "New table opened. Waiting command:\n";
    private final static String OUTPUT_PATTERN = "Output:\n%s\n";
    private final static String NULL = "null";
    private final static String INVALID_COMMAND_MESSAGE =
            "Invalid command format. Use 'help' to see all available commands";
    private final static String INVALID_CELL_FORMAT =
            "Invalid cell format";
    private final static String INVALUD_CELL_ADDRESS =
            "Invalid cell address";
    private final static String HELP_MESSAGE =
            "Available commands:\n" +
                    "   GET <cell>                      to get cell value,\n" +
                    "   SET <cell> '<value>'            to set cell value,\n" +
                    "   SETFORMULA <cell> '<formula>'   to set formula into cell,\n" +
                    "   DISPLAY                         to display table,\n" +
                    "   HELP                            to write all available commands,\n" +
                    "   EXIT                            to exit from application.\n";
}
