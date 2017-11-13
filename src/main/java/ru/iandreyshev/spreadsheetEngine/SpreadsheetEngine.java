package ru.iandreyshev.spreadsheetEngine;

import ru.iandreyshev.spreadsheetEngine.command.CommandBuilder;
import ru.iandreyshev.spreadsheetEngine.command.CommandType;
import ru.iandreyshev.spreadsheetEngine.command.ICommand;
import ru.iandreyshev.spreadsheetEngine.table.Table;
import ru.iandreyshev.spreadsheetEngine.table.Address;
import ru.iandreyshev.spreadsheetEngine.log.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

public class SpreadsheetEngine {
    private static final Address TOP_LEFT_CELL = new Address(Table.MIN_ROW_COUNT, Table.MIN_COL_COUNT);
    private static final Address BOTTOM_RIGHT_CELL = new Address(10, 10);
    private static final HashMap<CommandType, Consumer<ICommand>> interpreter = new HashMap<>();
    private static final String EXIT_MESSAGE = "Exit from table... Goodbye!";
    private static Table table = new Table(BOTTOM_RIGHT_CELL);

    public static void main(String[] args) {
        try {
            createCommandInterpreter();
            handleInput();
        } catch (Exception e) {
            log(new ExceptionLogEvent(e));
        }
    }

    private static void createCommandInterpreter() {
        interpreter.put(CommandType.GET, (cmd) -> processGET(cmd.getCell()));
        interpreter.put(CommandType.SET, (cmd) -> processSET(cmd.getCell(), cmd.getValue()));
        interpreter.put(CommandType.FORMULA, (cmd) -> processFormula(cmd.getCell(), cmd.getValue()));
        interpreter.put(CommandType.DISPLAY, (cmd) -> processDisplay());
        interpreter.put(CommandType.HELP, (cmd) -> log(new HelpLogEvent()));
        interpreter.put(CommandType.INVALID, (cmd) -> log(new InvalidCommandLogEvent()));
    }

    private static void handleInput() throws IOException {
        log(new StartLogEvent(TOP_LEFT_CELL, BOTTOM_RIGHT_CELL));
        InputStreamReader stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(stream);
        String input;
        log(new WaitCommandLogEvent());

        while ((input = reader.readLine()) != null) {
            ICommand cmd = CommandBuilder.parse(input);

            if (cmd.getType() == CommandType.EXIT) {
                break;
            }
            interpreter
                    .getOrDefault(cmd.getType(), c -> log(new InvalidCommandLogEvent()))
                    .accept(cmd);
            log(new WaitCommandLogEvent());
        }
        log(new PrintLogEvent(EXIT_MESSAGE));
    }

    private static void processGET(String addressStr) {
        if (!isAddressValid(addressStr)) {
            log(new InvalidCellAddressLogEvent(TOP_LEFT_CELL, BOTTOM_RIGHT_CELL));
            return;
        }
        Address address = Address.parse(addressStr);
        String cell = table.getValue(address);
        log(new PrintLogEvent(cell));
    }

    private static void processSET(String addressStr, String valueStr) {
        if (!isAddressValid(addressStr)) {
            log(new InvalidCellAddressLogEvent(TOP_LEFT_CELL, BOTTOM_RIGHT_CELL));
            return;
        }
        Address address = Address.parse(addressStr);
        table.setSimple(address, valueStr);
    }

    private static void processFormula(String addressStr, String value) {
        if (!isAddressValid(addressStr)) {
            log(new InvalidCellAddressLogEvent(TOP_LEFT_CELL, BOTTOM_RIGHT_CELL));
            return;
        }
        Address address = Address.parse(addressStr);

        try {
            table.setFormula(address, value);
        } catch (IllegalArgumentException e) {
            log(new InvalidFormulaFormatLogEvent(value));
        }
    }

    private static void processDisplay() {
    }

    private static boolean isAddressValid(String addressStr) {
        if (!Address.tryParse(addressStr)) {
            return false;
        }
        Address address = Address.parse(addressStr);
        return table.isAddressValid(address);
    }

    private static void log(LogEvent event) {
        System.out.print(event);
    }
}
