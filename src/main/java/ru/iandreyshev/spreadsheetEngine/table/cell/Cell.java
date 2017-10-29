package ru.iandreyshev.spreadsheetEngine.table.cell;

public class Cell {
    public Cell(CellType value) {
        set(value);
    }

    public CellType get() {
        return value;
    }

    public void set(CellType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    private CellType value;
}
