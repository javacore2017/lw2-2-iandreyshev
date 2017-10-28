package ru.iandreyshev.spreadsheetEngine;

import ru.iandreyshev.spreadsheetEngine.command.CommandBuilder;
import ru.iandreyshev.spreadsheetEngine.command.CommandType;
import ru.iandreyshev.spreadsheetEngine.command.ICommand;
import ru.iandreyshev.spreadsheetEngine.table.Table;
import ru.iandreyshev.spreadsheetEngine.util.Logger;
import ru.iandreyshev.spreadsheetEngine.util.Util;
import ru.iandreyshev.spreadsheetEngine.util.Vec2i;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

public class SpreadsheetEngine {
    public static void main(String[] args) {
        try {
            registerCommands();
            handleInput();
        } catch (Exception e) {
            System.out.printf("Catch exception: %s", e.getMessage());
            System.exit(EXIT_FAILED);
        }
        System.exit(EXIT_SUCCESS);
    }

    private Table table = new Table();
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILED = 1;
    private static final HashMap<CommandType, Consumer<ICommand>> commandEvent = new HashMap<>();

    private static void registerCommands() {
        commandEvent.put(CommandType.GET, (ICommand cmd) -> processGET(cmd.getCell()));
        commandEvent.put(CommandType.SET, (ICommand cmd) -> processSET(cmd.getCell(), cmd.getValue()));
        commandEvent.put(CommandType.FORMULA, (ICommand cmd) -> processFormula(cmd.getCell(), cmd.getValue()));
        commandEvent.put(CommandType.DISPLAY, (ICommand cmd) -> processDisplay());
        commandEvent.put(CommandType.HELP, (ICommand cmd) -> Logger.help());
    }

    private static void handleInput() throws IOException {
        Logger.start();
        InputStreamReader stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(stream);
        String input;

        while ((input = reader.readLine()) != null) {
            ICommand command = CommandBuilder.parse(input);
            if (command.getType() == CommandType.EXIT) {
                return;
            } else if (commandEvent.containsKey(command.getType())) {
                commandEvent.get(command.getType()).accept(command);
            } else {
                Logger.invalidCommand();
            }
        }
    }

    private static void processGET(String address) {
    }

    private static void processSET(String address, String value) {
    }

    private static void processFormula(String address, String value) {
    }

    private static void processDisplay() {
    }
}
