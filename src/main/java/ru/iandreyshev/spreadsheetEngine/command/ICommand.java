package ru.iandreyshev.spreadsheetEngine.command;

public interface ICommand {
    CommandType getType();

    String getCell();

    String getValue();
}
