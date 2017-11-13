package ru.iandreyshev.spreadsheetEngine.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

public class Table {
    public static final int MIN_ROW_COUNT = 1;
    public static final int MIN_COL_COUNT = 1;

    private ArrayList<ArrayList<CellType>> table;

    public Table(Address bottomRightAddress) {
        int rowCount = bottomRightAddress.getRow();
        int colCount = bottomRightAddress.getCol();

        rowCount = (rowCount < MIN_ROW_COUNT) ? MIN_ROW_COUNT : rowCount;
        colCount = (colCount < MIN_COL_COUNT) ? MIN_COL_COUNT : colCount;

        table = new ArrayList<>();

        for (int row = 0; row < rowCount; ++row) {
            table.add(new ArrayList<>());
            for (int col = 0; col < colCount; ++col) {
                Str value = new Str();
                value.setFromStr("");
                table.get(row).add(value);
            }
        }
    }

    public String getValue(Address address) throws IndexOutOfBoundsException {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Invalid address");
        }
        return get(address) == null ? "" : get(address).toString();
    }

    public void setSimple(Address address, String str)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        validateOnSet(address, str);
        trySetStr(address, str);
        trySetDat(address, str);
        trySetInt(address, str);
    }

    public void setFormula(Address address, String str)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        validateOnSet(address, str);
        Formula formula = new Formula();
        if (!formula.setFromStr(str)) {
            throw new IllegalArgumentException("Invalid formula format");
        }
    }

    public boolean isAddressValid(Address address) {
        if (address == null) {
            return false;
        }
        if (address.getRow() < MIN_ROW_COUNT || address.getRow() > rowCount()) {
            return false;
        } else if (address.getCol() < MIN_COL_COUNT || address.getCol() > colCount()) {
            return false;
        }
        return true;
    }

    private int rowCount() {
        return table.size();
    }

    private int colCount() {
        return table.get(0).size();
    }

    private CellType get(Address address) {
        return table.get(address.getRow() - 1).get(address.getCol() - 1);
    }

    private void set(Address address, CellType value) {
        table.get(address.getRow() - 1).set(address.getCol() - 1, value);
    }

    private void validateOnSet(Address address, String valueStr)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Invalid address");
        } else if (valueStr == null) {
            throw new IllegalArgumentException("Value string is null");
        }
    }

    private void trySetStr(Address address, String str) {
        Str value = new Str();
        if (value.setFromStr(str)) {
            set(address, value);
        }
    }

    private void trySetDat(Address address, String str) {
        Dat value = new Dat();
        if (value.setFromStr(str)) {
            set(address, value);
        }
    }

    private void trySetInt(Address address, String str) {
        Int value = new Int();
        if (value.setFromStr(str)) {
            set(address, value);
        }
    }
}
