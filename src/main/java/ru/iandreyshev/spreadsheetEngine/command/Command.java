package ru.iandreyshev.spreadsheetEngine.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command implements ICommand {
    private static final int COMMAND_GROUP_NUM = 1;
    private static final int CELL_GROUP_NUM = 2;
    private static final int VALUE_GROUP_NUM = 3;
    private static final String COMMAND_REGEX = " *([\\S]+) *([a-zA-Z0-9]+)* *(:[\\S- ]*)* *";
    private static final String GET_COMMAND = "get";
    private static final String SET_COMMAND = "set";
    private static final String FORMULA_COMMAND = "formula";
    private static final String DISPLAY_COMMAND = "display";
    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";
    private static final Pattern PATTERN = Pattern.compile(COMMAND_REGEX);

    private CommandType type = CommandType.INVALID;
    private String cell = "";
    private String value = "";

    public static ICommand parse(String input) throws IllegalArgumentException {
        Command command = new Command();

        if (input == null) {
            return command;
        }
        try {
            command.parseCommand(input);
        } catch (Exception ex) {
            command.type = CommandType.INVALID;
        }

        return command;
    }

    public CommandType getType() {
        return type;
    }

    public String getCell() {
        return cell;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Command type: [" + type.toString() + "]\n";
        result += "Cell: [" + cell + "]\n";
        result += "Value: [" + value + "]\n";

        return result;
    }

    private Command() {}

    private void parseCommand(String input) {
        Matcher matcher = PATTERN.matcher(input);
        matcher.find();
        assignType(matcher.group(COMMAND_GROUP_NUM));
        cell = matcher.group(CELL_GROUP_NUM);
        value = matcher.group(VALUE_GROUP_NUM);

        if (value != null && value.length() > 0) {
            value = value.substring(1);
        }
    }

    private void assignType(String cmd) {
        cmd = cmd.toLowerCase();
        type = CommandType.INVALID;

        if (cmd.compareTo(GET_COMMAND) == 0) {
            type = CommandType.GET;
        } else if (cmd.compareTo(SET_COMMAND) == 0) {
            type = CommandType.SET;
        } else if (cmd.compareTo(FORMULA_COMMAND) == 0) {
            type = CommandType.FORMULA;
        } else if (cmd.compareTo(DISPLAY_COMMAND) == 0) {
            type = CommandType.DISPLAY;
        } else if (cmd.compareTo(HELP_COMMAND) == 0) {
            type = CommandType.HELP;
        } else if (cmd.compareTo(EXIT_COMMAND) == 0) {
            type = CommandType.EXIT;
        }
    }
}
