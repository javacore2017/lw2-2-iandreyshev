package ru.iandreyshev.spreadsheetEngine.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBuilder implements ICommand {
    public static ICommand parse(String input) throws IllegalArgumentException {
        CommandBuilder command = new CommandBuilder();
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

    public String getCommand() {
        return command;
    }

    public String getCell() {
        return cell;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Command type: [" + command.toString() + "]\n");
        builder.append("Cell: [" + cell + "]\n");
        builder.append("Value: [" + value + "]\n");
        return builder.toString();
    }

    private static final String GET_COMMAND = "get";
    private static final String SET_COMMAND = "set";
    private static final String FORMULA_COMMAND = "formula";
    private static final String DISPLAY_COMMAND = "display";
    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";

    private CommandType type = CommandType.INVALID;
    private String command = "";
    private String cell = "";
    private String value = "";

    private CommandBuilder() {}

    private void parseCommand(String input) {
        Matcher matcher = Pattern.compile(COMMAND_REGEX).matcher(input);
        matcher.matches();

        if (matcher.groupCount() < MIN_GROUPS_IN_COMMAND) {
            return;
        }
        assignCommand(matcher.group(COMMAND_GROUP_NUM));

        if (type == CommandType.GET) {
            if (matcher.groupCount() < CELL_GROUP_NUM ||
                    matcher.group(CELL_GROUP_NUM) == null) {
                type = CommandType.INVALID;
                return;
            }
            cell = matcher.group(CELL_GROUP_NUM);
        } else if (type == CommandType.SET || type == CommandType.FORMULA) {
            if (matcher.groupCount() < VALUE_GROUP_NUM ||
                    matcher.group(CELL_GROUP_NUM) == null ||
                    matcher.group(VALUE_GROUP_NUM) == null) {
                type = CommandType.INVALID;
                return;
            }
            cell = matcher.group(CELL_GROUP_NUM);
            value = matcher.group(VALUE_GROUP_NUM);
        }
    }

    private void assignCommand(String commandStr) {
        command = commandStr.toLowerCase();
        type = CommandType.INVALID;

        if (command.compareTo(GET_COMMAND) == 0) {
            type = CommandType.GET;
        } else if (command.compareTo(SET_COMMAND) == 0) {
            type = CommandType.SET;
        } else if (command.compareTo(FORMULA_COMMAND) == 0) {
            type = CommandType.FORMULA;
        } else if (command.compareTo(DISPLAY_COMMAND) == 0) {
            type = CommandType.DISPLAY;
        } else if (command.compareTo(HELP_COMMAND) == 0) {
            type = CommandType.HELP;
        } else if (command.compareTo(EXIT_COMMAND) == 0) {
            type = CommandType.EXIT;
        }
    }

    private static int MIN_GROUPS_IN_COMMAND = 1;
    private static int COMMAND_GROUP_NUM = 1;
    private static int CELL_GROUP_NUM = 2;
    private static int VALUE_GROUP_NUM = 3;
    private static String COMMAND_REGEX = " *([\\S]+) *([\\S]+)* *('[\\S- ]*')* *";
}