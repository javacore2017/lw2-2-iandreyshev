package ru.iandreyshev.spreadsheetEngine.log;

public class HelpLogEvent extends LogEvent {
    public HelpLogEvent() {
        add(MESSAGE);
    }

    private final static String MESSAGE =
            "Available commands:\n" +
                    "   GET <cell>                      to get cell value,\n" +
                    "   SET <cell> :<value>            to set cell value,\n" +
                    "   SETFORMULA <cell> :<formula>   to set formula into cell,\n" +
                    "   DISPLAY                         to display table,\n" +
                    "   HELP                            to write all available commands,\n" +
                    "   EXIT                            to exit from application.\n";
}
