package ru.iandreyshev.spreadsheetEngine.table.cell;

public abstract class Cell<T> {
    private T value;

    public final boolean setFromStr(String str) {
        if (!tryParse(str)) {
            return false;
        }
        value = parse(str);

        return true;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public T get() {
        return value;
    }

    final void set(T value) {
        this.value = value;
    }

    abstract T parse(String str);

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
}
