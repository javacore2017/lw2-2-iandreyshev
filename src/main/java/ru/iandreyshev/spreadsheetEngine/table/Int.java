package ru.iandreyshev.spreadsheetEngine.table;

final class Int extends CellType<Integer> {
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
    protected Integer parse(String str) throws IllegalArgumentException {
        return Integer.parseInt(str.trim());
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
