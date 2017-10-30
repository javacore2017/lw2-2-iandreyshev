package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Int extends CellType<Integer> {
    public Int (String valueStr) throws IllegalArgumentException {
        super(valueStr);
    }

    @Override
    public void add(CellType value) {
        Integer anotherVal = tryCast(value);
        if (anotherVal == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() + anotherVal);
    }

    @Override
    public void div(CellType value) {
        Integer anotherVal = tryCast(value);
        if (anotherVal == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() - anotherVal);
    }

    @Override
    public void mul(CellType value) {
        Integer anotherVal = tryCast(value);
        if (anotherVal == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        }
        this.set(this.get() * anotherVal);
    }

    @Override
    public void sub(CellType value) {
        Integer anotherVal = tryCast(value);
        if (anotherVal == null) {
            throw new IllegalArgumentException("Invalid cell type cast");
        } else if (anotherVal == 0) {
            throw new IllegalArgumentException("Divide by zero");
        }
        this.set(this.get() / anotherVal);
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @Override
    protected Integer parse(String valueStr) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder(valueStr);
        String trimmed = builder.toString().trim();
        return Integer.parseInt(trimmed);
    }

    private Integer tryCast(Object cellValue) {
        Integer result;
        try {
            result = (Integer) cellValue;
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
