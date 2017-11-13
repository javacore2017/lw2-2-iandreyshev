package ru.iandreyshev.spreadsheetEngine.table;

abstract class CellType<T> {
    private T value;

    @Override
    public String toString() {
        return value.toString();
    }

    final T get() {
        return value;
    }

    final void set(T value) {
        this.value = value;
    }

    final boolean setFromStr(String str) {
        if (!tryParse(str)) {
            return false;
        }
        value = parse(str);

        return true;
    }

    private boolean tryParse(String str) {
        try {
            T val = parse(str);

            if (val == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    void add(CellType value) {}

    void sub(CellType value) {}

    void mul(CellType value) {}

    void div(CellType value) {}

    abstract T parse(String str);
}
