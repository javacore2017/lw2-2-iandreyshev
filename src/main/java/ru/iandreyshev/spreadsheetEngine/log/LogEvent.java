package ru.iandreyshev.spreadsheetEngine.log;

public class LogEvent {
    private String message = OUTPUT_PATTERN;
    private final static String OUTPUT_PATTERN = "> ";
    private final static String ITEM_MARKER = "  ";

    @Override
    public String toString() {
        return message;
    }

    LogEvent() { }

    void addString(String str) {
        message += str + "\n";
    }

    void addText(String text) {
        message += text;
    }

    void addListItem(String item) {
        message += ITEM_MARKER + item + "\n";
    }
}
