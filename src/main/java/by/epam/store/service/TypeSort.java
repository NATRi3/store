package by.epam.store.service;


public enum TypeSort {
    PRICE("price"),
    PRICEDECS("price DESC"),
    NAME("name"),
    NAMEDECS("name DESC"),
    DATE("date"),
    DATEDESC("date DESC"),
    TITLE("title"),
    TITLEDESC("title DESC");
    private final String string;

    TypeSort(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
