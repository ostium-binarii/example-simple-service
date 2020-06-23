package reporting.datautilities;


/**
 * String names of column headers in the given CSV file.
 */
public enum CsvFieldNames {
    COMPANY_CODE("company_ticker"),
    CLOSING_PRICE_DATE("date"),
    CLOSING_PRICE("closing_price");

    private final String name;

    CsvFieldNames(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
