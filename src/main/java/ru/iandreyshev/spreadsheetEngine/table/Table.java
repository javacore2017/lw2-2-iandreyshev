package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cell.Cell;
import ru.iandreyshev.spreadsheetEngine.table.cell.CellType;
import ru.iandreyshev.spreadsheetEngine.table.cell.Str;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {
    public static TableAddress toAddress(String address) throws IllegalArgumentException {
        if (address == null) {
            throw new IllegalArgumentException("Address is null");
        }
        Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(address);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid cell address format");
        }

        int row;
        int col;

        try {
            row = Integer.parseUnsignedInt(matcher.group(ROW_GROUP));
            col = matcher.group(COL_GROUP).toLowerCase().charAt(0) - (int)'a';
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cell address format");
        }

        return new TableAddress(row, col);
    }

    public Table(int rowCount, int colCount) {
        rowCount = (rowCount < 0) ? 0 : rowCount;
        colCount = (colCount < 0) ? 0 : colCount;
        table = new ArrayList<>();
        for (int row = 0; row < rowCount; ++row) {
            table.add(new ArrayList<>());
            for (int col = 0; col < colCount; ++col) {
                table.get(row).add(new Cell(new Str(new TableAddress(row, col).toString())));
            }
        }
    }

    public boolean isAddressValid(TableAddress address) {
        if (address.getRow() < 0 || address.getRow() > table.size() - 1) {
            return false;
        }
        if (address.getCol() < 0 || address.getCol() > table.size() - 1) {
            return false;
        }
        return true;
    }

    public Cell get(TableAddress address) {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Invalid cell address");
        }
        return table.get(address.getRow()).get(address.getCol());
    }

    public void forEach(BiConsumer<Cell, TableAddress> action) {
        for (int row = 0; row < table.size(); ++row) {
            for (int col = 0; col < table.size(); ++col) {
                final TableAddress address = new TableAddress(row, col);
                action.accept(table.get(row).get(col), address);
            }
        }
    }

    private static final int TABLE_COL_RADIX = 26;
    private static final String ADDRESS_REGEX = "( *)([0-9]+)([a-zA-Z])( *)";
    private static final int ROW_GROUP = 2;
    private static final int COL_GROUP = 3;

    private ArrayList<ArrayList<Cell>> table;
}
