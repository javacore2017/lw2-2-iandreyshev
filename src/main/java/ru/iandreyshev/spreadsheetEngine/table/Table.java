package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cell.*;

import java.util.*;

public class Table {
    public static final int MIN_ROW_COUNT = 1;
    public static final int MIN_COL_COUNT = 1;

    private List<List<Cell>> table;
    private HashMap<Address, HashSet<Address>> expressionByLink = new HashMap<>();

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
                table.get(row).add(value);
            }
        }
    }

    public String getValue(Address address) throws IndexOutOfBoundsException {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Invalid address");
        }
        Cell value = get(address);

        return value == null ? Formula.UNDEFINED : value.toString();
    }

    public List<List<Cell>> getTable() {
        return table;
    }

    int rowCount() {
        return table.size();
    }

    int colCount() {
        return table.get(0).size();
    }

    public void setSimple(Address address, String valueStr)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        validateOnSet(address, valueStr);
        set(address, toAnyType(valueStr));

        for (Address link : expressionByLink.getOrDefault(address, new HashSet<>())) {
            calcFormulaIn(link);
        }
    }

    public void setFormula(Address address, String str)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        validateOnSet(address, str);
        Formula formula = new Formula();

        if (!formula.setFromStr(str)) {
            throw new IllegalArgumentException("Invalid formula format");
        } else if (!isFormulaAddressesAllowed(formula.getCells())) {
            throw new IndexOutOfBoundsException("Address out of bounds");
        } else if (!isRecursionFree(address, formula.getCells())) {
            throw new IllegalArgumentException("Recursion is not allowed");
        }
        set(address, formula);
        addToMemory(address, formula);
        calcFormulaIn(address);
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

    private Cell get(Address address) {
        return table.get(address.getRow() - 1).get(address.getCol() - 1);
    }

    private void set(Address address, Cell value) {
        removeFromMemory(address);
        table.get(address.getRow() - 1).set(address.getCol() - 1, value);
    }

    private void validateOnSet(Address address, String valueStr)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (!isAddressValid(address)) {
            throw new IndexOutOfBoundsException("Address out of bounds");
        } else if (valueStr == null) {
            throw new IllegalArgumentException("Value string is null");
        }
    }

    private Cell toAnyType(String value) {
        Cell result = new Str(value);
        Num intResult = new Num();

        if (intResult.setFromStr(value)) {
            result = intResult;
        }
        Dat datResult = new Dat();

        if (datResult.setFromStr(value)) {
            result = datResult;
        }

        return result;
    }

    private void addToMemory(Address address, Formula formula) {
        for (Address cell : formula.getCells()) {
            HashSet<Address> addresses = expressionByLink.getOrDefault(cell, new HashSet<>());
            addresses.add(address);
            expressionByLink.put(cell, addresses);
        }
    }

    private void removeFromMemory(Address address) {
        Cell currValue = get(address);

        if (currValue instanceof Formula) {
            Formula formula = (Formula) currValue;

            for (Address cell : formula.getCells()) {
                expressionByLink.get(address).remove(cell);
            }
        }
    }

    private void calcFormulaIn(Address address) throws IllegalArgumentException {
        if (!(get(address) instanceof Formula)) {
            return;
        }
        Formula formula = (Formula) get(address);
        Stack<Cell> stack = new Stack<>();

        for (String token : formula.getTokens()) {
            if (!Arithmetic.isSign(token)) {
                if (Address.tryParse(token)) {
                    stack.add(get(Address.parse(token)));
                } else {
                    stack.add(toAnyType(token));
                }
                continue;
            }
            Cell first = stack.pop();
            Cell second = stack.pop();
            try {
                stack.push(Arithmetic.calc(first, second, token));
            } catch (Exception ex) {
                formula.setResult(null);
                return;
            }
        }
        formula.setResult(stack.peek());
    }

    private boolean isRecursionFree(Address start, HashSet<Address> addressTokens) {
        if (addressTokens.contains(start)) {
            return false;
        }

        HashSet<Address> passedCells = new HashSet<>();
        Stack<Address> cellsQueue = new Stack<>();
        cellsQueue.addAll(addressTokens);

        while (!cellsQueue.isEmpty()) {
            Address current = cellsQueue.pop();

            if (passedCells.contains(current)) {
                continue;
            }
            passedCells.add(current);
            Cell currentValue = get(current);

            if (!(currentValue instanceof Formula)) {
                continue;
            }
            Formula formula = (Formula) currentValue;

            for (Address cell : formula.getCells()) {
                if (cell.equals(start)) {
                    return false;
                }
                cellsQueue.add(cell);
            }
        }

        return true;
    }

    private boolean isFormulaAddressesAllowed(HashSet<Address> formulaAddresses) {
        for (Address address : formulaAddresses) {
            if (!isAddressValid(address)) {
                return false;
            }
        }

        return true;
    }
}
