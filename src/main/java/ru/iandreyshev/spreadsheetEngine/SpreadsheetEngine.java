package ru.iandreyshev.spreadsheetEngine;

import ru.iandreyshev.spreadsheetEngine.command.CommandBuilder;
import ru.iandreyshev.spreadsheetEngine.command.CommandType;
import ru.iandreyshev.spreadsheetEngine.command.ICommand;
import ru.iandreyshev.spreadsheetEngine.table.Table;
import ru.iandreyshev.spreadsheetEngine.table.cell.*;
import ru.iandreyshev.spreadsheetEngine.table.exception.IllegalExpression;
import ru.iandreyshev.spreadsheetEngine.table.util.Address;
import ru.iandreyshev.spreadsheetEngine.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

public class SpreadsheetEngine {
    public static void main(String[] args) {
        try {
            createCommandInterpreter();
            handleInput();
        } catch (Exception e) {
            Logger.exception(e.getMessage());
            System.exit(EXIT_FAILED);
        }
        System.exit(EXIT_SUCCESS);
    }

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILED = 1;
    private static final int ROW_COUNT = 10;
    private static final int COL_COUNT = 10;
    private static Table table = new Table(ROW_COUNT, COL_COUNT);
    private static final HashMap<CommandType, Consumer<ICommand>> interpreter = new HashMap<>();

    private static void createCommandInterpreter() {
        interpreter.put(CommandType.GET, (cmd) -> processGET(cmd.getCell()));
        interpreter.put(CommandType.SET, (cmd) -> processSET(cmd.getCell(), cmd.getValue()));
        interpreter.put(CommandType.FORMULA, (cmd) -> processFormula(cmd.getCell(), cmd.getValue()));
        interpreter.put(CommandType.DISPLAY, (cmd) -> processDisplay());
        interpreter.put(CommandType.HELP, (cmd) -> Logger.help());
        interpreter.put(CommandType.INVALID, (cmd) -> Logger.invalidCommand());
    }

    private static void handleInput() throws IOException {
        Logger.start();
        InputStreamReader stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(stream);
        String input;
        Logger.waitCmd();

        while ((input = reader.readLine()) != null) {
            ICommand cmd = CommandBuilder.parse(input);
            if (cmd.getType() == CommandType.EXIT) {
                return;
            }
            Consumer<ICommand> event = interpreter.getOrDefault(cmd.getType(), (c) -> {
                Logger.invalidCommand();
            });
            event.accept(cmd);
            Logger.waitCmd();
        }
    }

    private static void processGET(String addressStr) {
        if (!isAddressStrValid(addressStr)) {
            Logger.invalidCellAddress(table.rowCount(), table.colCount());
            return;
        }
        try {
            Address address = Address.parse(addressStr);
            Cell cell = table.get(address);
            Logger.print(cell.toString());
        } catch (IndexOutOfBoundsException e) {
            Logger.invalidCellAddress(table.rowCount(), table.colCount());
        }
    }

    private static void processSET(String addressStr, String valueStr) {
        if (!isAddressStrValid(addressStr)) {
            Logger.invalidCellAddress(table.rowCount(), table.colCount());
            return;
        }

        CellType newValue = null;
        try {
            newValue = new Str(valueStr);
        } catch (Exception e) {}
        try {
            newValue = new Dat(valueStr);
        } catch (Exception e) {}
        try {
            newValue = new Int(valueStr);
        } catch (Exception e) {}

        Address address = Address.parse(addressStr);
        if (newValue == null) {
            Logger.invalidCellFormat();
        } else if (table.set(address, newValue)) {
            // Logger.successSet(address, newValue);
        } else {
            Logger.invalidCellFormat();
        }
    }

    private static void processFormula(String addressStr, String value) {
        if (!isAddressStrValid(addressStr)) {
            Logger.invalidCellAddress(table.rowCount(), table.colCount());
            return;
        }

        CellType newValue = null;
        try {
            newValue = new Formula(value);
        } catch (IllegalArgumentException e) {
            Logger.invalidCellFormat();
        }

        Address address = Address.parse(addressStr);
        if (!table.set(address, newValue)) {
            // Logger.successSet(address, newValue);
        } else {
            // Logger.invalidFormula;
        }
    }

    private static void processDisplay() {
    }

    private static boolean isAddressStrValid(String addressStr) {
        if (!Address.tryParse(addressStr)) {
            return false;
        }
        Address address = Address.parse(addressStr);
        return table.isAddressValid(address);
    }
}
