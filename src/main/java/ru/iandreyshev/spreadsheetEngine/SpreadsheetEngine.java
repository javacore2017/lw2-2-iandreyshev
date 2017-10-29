package ru.iandreyshev.spreadsheetEngine;

import ru.iandreyshev.spreadsheetEngine.command.CommandBuilder;
import ru.iandreyshev.spreadsheetEngine.command.CommandType;
import ru.iandreyshev.spreadsheetEngine.command.ICommand;
import ru.iandreyshev.spreadsheetEngine.table.Table;
import ru.iandreyshev.spreadsheetEngine.table.TableAddress;
import ru.iandreyshev.spreadsheetEngine.table.cell.Cell;
import ru.iandreyshev.spreadsheetEngine.util.Logger;

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
            System.out.printf("Catch exception: %s", e.getMessage());
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

        while ((input = reader.readLine()) != null) {
            ICommand cmd = CommandBuilder.parse(input);
            if (cmd.getType() == CommandType.EXIT) {
                return;
            }
            Consumer<ICommand> event = interpreter.getOrDefault(cmd.getType(), (c) -> {
                Logger.invalidCommand();
            });
            event.accept(cmd);
        }
    }

    private static void processGET(String addressStr) {
        try {
            TableAddress address =  Table.toAddress(addressStr);
            Cell cell = table.get(address);
            Logger.output(cell);
        } catch (IllegalArgumentException e) {
            Logger.invalidCellFormat();
        } catch (IndexOutOfBoundsException e) {
            Logger.invalidCellAddress();
        }
    }

    private static void processSET(String address, String value) {
    }

    private static void processFormula(String address, String value) {
    }

    private static void processDisplay() {
    }
}
