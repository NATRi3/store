package by.epam.store.service;


/**
 * The enum Type sort.
 */
public enum TypeSort {
    /**
     * Price type sort.
     */
    PRICE("price", false),
    /**
     * Price desc type sort.
     */
    PRICEDESC("price", true),
    /**
     * Name type sort.
     */
    NAME("name", false),
    /**
     * Name desc type sort.
     */
    NAMEDESC("name", true),
    /**
     * Date type sort.
     */
    DATE("date", false),
    /**
     * Date desc type sort.
     */
    DATEDESC("date", true),
    /**
     * Title type sort.
     */
    TITLE("title", false),
    /**
     * Title desc type sort.
     */
    TITLEDESC("title", true);
    private final String string;
    private final boolean isDesc;

    TypeSort(String string, boolean isDesc) {
        this.string = string;
        this.isDesc = isDesc;
    }

    /**
     * Gets string.
     *
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * Is desc boolean.
     *
     * @return the boolean
     */
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
