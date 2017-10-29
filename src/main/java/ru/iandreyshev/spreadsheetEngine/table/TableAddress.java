package ru.iandreyshev.spreadsheetEngine.table;

public class TableAddress {
    TableAddress(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "[" + row + ", " + col +"]";
    }

    private int row = 0;
    private int col = 0;
}
