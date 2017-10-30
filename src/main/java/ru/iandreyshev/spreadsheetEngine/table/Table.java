package ru.iandreyshev.spreadsheetEngine.table;

import javafx.util.Pair;
import ru.iandreyshev.spreadsheetEngine.table.cell.*;
import ru.iandreyshev.spreadsheetEngine.table.exception.IllegalExpression;
import ru.iandreyshev.spreadsheetEngine.table.util.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

public class Table {
    public Table(int rowCount, int colCount) {
        rowCount = (rowCount < MIN_ROW_COUNT) ? MIN_ROW_COUNT : rowCount;
        colCount = (colCount < MIN_COL_COUNT) ? MIN_COL_COUNT : colCount;

        table = new ArrayList<>();

        for (int row = 0; row < rowCount; ++row) {
            table.add(new ArrayList<>());
            for (int col = 0; col < colCount; ++col) {
                table.get(row).add(new Cell(INIT_CELL_VALUE));
            }
        }
    }

    public boolean isAddressValid(Address address) {
        if (address == null) {
            return false;
        } if (address.getRow() < 0 || address.getRow() > table.size() - 1) {
            return false;
        } else if (address.getCol() < 0 || address.getCol() > table.size() - 1) {
            return false;
        }
        return true;
    }

    public Cell get(Address address) throws IndexOutOfBoundsException {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Invalid cell address");
        }
        return table.get(address.getRow()).get(address.getCol());
    }

    public boolean set(Address address, CellType value)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (value == null) {
            throw new IllegalArgumentException("Try set to cell null value");
        } else if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Cell address is out of range");
        }

        if (value instanceof Int) {
            setValue(address, value);
        } else if (value instanceof Str) {
            setValue(address, value);
        } else if (value instanceof Dat) {
            setValue(address, value);
        } else if (value instanceof Formula) {
            setExpression(address, (Formula)value);
        } else {
            return false;
        }
        return true;
    }

    public void forEach(BiConsumer<Cell, Address> action) {
        for (int row = 0; row < table.size(); ++row) {
            for (int col = 0; col < table.size(); ++col) {
                final Address address = new Address(row, col);
                action.accept(table.get(row).get(col), address);
            }
        }
    }

    public int rowCount() {
        return table.size();
    }

    public int colCount() {
        return rowCount() == 0 ? 0 : table.get(0).size();
    }

    private static final int MIN_ROW_COUNT = 1;
    private static final int MIN_COL_COUNT = 1;
    private static final CellType INIT_CELL_VALUE = new Str("");

    private ArrayList<ArrayList<Cell>> table;
    private HashMap<Address, HashSet<Address>> expressionMemory;

    private void setValue(Address address, CellType value) {
        table.get(address.getRow()).get(address.getCol()).set(value);
    }

    private boolean setExpression(Address address, Formula expression) {
        return false;
    }
}
