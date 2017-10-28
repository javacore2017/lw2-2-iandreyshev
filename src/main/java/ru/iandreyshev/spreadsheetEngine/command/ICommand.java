package ru.iandreyshev.spreadsheetEngine.command;

import ru.iandreyshev.spreadsheetEngine.util.Vec2i;

public interface ICommand {
    CommandType getType();

    String getCell();

    String getValue();

    String getCommand();
}
