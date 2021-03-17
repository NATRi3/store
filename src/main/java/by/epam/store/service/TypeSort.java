package by.epam.store.service;


public enum TypeSort {
    PRICE("price", false),
    PRICEDESC("price", true),
    NAME("name", false),
    NAMEDESC("name", true),
    DATE("date", false),
    DATEDESC("date", true),
    TITLE("title", false),
    TITLEDESC("title", true);
    private final String string;
    private final boolean isDesc;

    TypeSort(String string, boolean isDesc) {
        this.string = string;
        this.isDesc = isDesc;
    }

    public String getString() {
        return string;
    }

    public boolean isDesc() {
        return isDesc;
    }

    @Override
    public String toString() {
        if(isDesc) {
            return string + " DESC";
        }
        return string;
    }
}
