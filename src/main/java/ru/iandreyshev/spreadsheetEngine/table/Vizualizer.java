package ru.iandreyshev.spreadsheetEngine.table;

import ru.iandreyshev.spreadsheetEngine.table.cellType.CellType;
import java.util.List;

public final class Vizualizer {
    private static final int CELL_WIDTH = 14;
    private static final String TRIM_TAIL = "...";
    private static final char BORDER_VERTICAL = '|';
    private static final char EMPTY = ' ';
    private static final char BORDER_HORIZONTAL = '_';

    public static void draw(Table table) {
        final int colsCount = table.colCount();

        if (table.rowCount() > 0) {
            drawRow(colsCount, BORDER_HORIZONTAL, true);
        }

        for (int i = 0; i < table.rowCount(); ++i) {
            drawRow(colsCount, EMPTY, false);
            drawRow(table.getTable().get(i));
            drawRow(colsCount, BORDER_HORIZONTAL, false);
        }
    }

    private static void drawRow(int length, char inner, boolean ignoreVerticalBorder) {
        final char verticalBorder = (ignoreVerticalBorder) ? inner : BORDER_VERTICAL;

        if (length > 0) {
            System.out.print(verticalBorder);
        }
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < CELL_WIDTH; ++j) {
                System.out.print(inner);
            }
            System.out.print(verticalBorder);
        }
        System.out.print('\n');
    }

    private static void drawRow(List<CellType> values) {
        if (values.size() > 0) {
            System.out.print(BORDER_VERTICAL);
        }
        for (CellType value : values) {
            System.out.print(toCellValue(value));
            System.out.print(BORDER_VERTICAL);
        }
        System.out.print('\n');
    }

    private static String toCellValue(CellType value) {
        String result = value.toString();

        if (result.length() > CELL_WIDTH) {
            final int end = CELL_WIDTH - TRIM_TAIL.length();
            result = result.substring(0, end > 0 ? end : 0);
            result = result + TRIM_TAIL;
        }

        StringBuilder builder = new StringBuilder(result);

        while (builder.length() < CELL_WIDTH) {
            builder.append(EMPTY);
        }

        return builder.toString();
    }
}
