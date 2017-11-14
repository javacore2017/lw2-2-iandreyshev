package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cellType.*;

import java.util.*;

public class Table {
    public static final int MIN_ROW_COUNT = 1;
    public static final int MIN_COL_COUNT = 1;

    private List<List<CellType>> table;
    private HashMap<String, HashSet<String>> expressionByLink = new HashMap<>();

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
        CellType value = get(address);

        return value == null ? Formula.UNDEFINED : value.toString();
    }

    public List<List<CellType>> getTable() {
        return table;
    }

    public int rowCount() {
        return table.size();
    }

    public int colCount() {
        return table.get(0).size();
    }

    public void setSimple(Address address, String valueStr)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        validateOnSet(address, valueStr);
        set(address, toAnyType(valueStr));

        for (String formulaAddress : expressionByLink.getOrDefault(address.toString(), new HashSet<>())) {
            calcFormulaIn(Address.parse(formulaAddress));
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
        } else if (!isRecursionFree(address, formula.getCellsTokens())) {
            throw new IllegalArgumentException("Recursion is not allowed");
        }

        set(address, formula);

        for (String addressToken : formula.getCellsTokens()) {
            HashSet<String> addresses = expressionByLink.getOrDefault(addressToken, new HashSet<>());
            addresses.add(address.toString());
            expressionByLink.put(addressToken, addresses);
        }

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

    private CellType get(Address address) {
        return table.get(address.getRow() - 1).get(address.getCol() - 1);
    }

    private void set(Address address, CellType value) {
        clearMemoryOfFormula(address);
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

    private CellType toAnyType(String value) {
        CellType result = new Str(value);
        Int intResult = new Int();

        if (intResult.setFromStr(value)) {
            result = intResult;
        }

        Dat datResult = new Dat();

        if (datResult.setFromStr(value)) {
            result = datResult;
        }

        return result;
    }

    private void clearMemoryOfFormula(Address address) {
        CellType currValue = get(address);

        if (currValue instanceof Formula) {
            Formula formula = (Formula) currValue;

            for (String addressToken : formula.getCellsTokens()) {
                expressionByLink.get(addressToken).remove(address.toString());
            }
        }
    }

    private void calcFormulaIn(Address address) throws IllegalArgumentException {
        CellType value = get(address);

        if (!(value instanceof Formula)) {
            return;
        }
        Formula formula = (Formula) value;
        Stack<String> stack = new Stack<>();
        stack.addAll(formula.getTokens());
        CellType result = Address.tryParse(stack.peek()) ? get(Address.parse(stack.peek())) : null;

        while (stack.size() > 2) {
            CellType second = get(Address.parse(stack.pop()));
            CellType first = get(Address.parse(stack.pop()));
            String sign = stack.pop();
            result = Arithmetic.calc(first, second, sign);
            stack.push(result == null ? Formula.UNDEFINED : result.toString());
        }

        formula.setResult(result);
    }

    private boolean isRecursionFree(Address start, HashSet<String> formulaAddressTokens) {
        if (formulaAddressTokens.contains(start.toString())) {
            return false;
        }

        HashSet<String> passedCells = new HashSet<>();
        Stack<String> cellsQueue = new Stack<>();
        cellsQueue.addAll(formulaAddressTokens);

        while (cellsQueue.size() > 0) {
            String current = cellsQueue.pop();

            if (passedCells.contains(current)) {
                continue;
            }
            passedCells.add(current);
            CellType currentValue = get(Address.parse(current));

            if (!(currentValue instanceof Formula)) {
                continue;
            }
            Formula formula = (Formula) currentValue;
            for (String addressToken : formula.getCellsTokens()) {
                if (addressToken.compareTo(start.toString()) == 0) {
                    return false;
                }
                cellsQueue.add(addressToken);
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
