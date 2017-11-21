package ru.iandreyshev.spreadsheetEngine.table.cell;

public final class Num extends Cell<Float> {
    public Num() {
        set(0.f);
    }

    public Num(Float value) {
        set(value);
    }

    @Override
    public String toString() {
        return get().toString();
    }

    @Override
    protected Float parse(String str) throws IllegalArgumentException {
        return Float.parseFloat(str.trim());
    }
}
