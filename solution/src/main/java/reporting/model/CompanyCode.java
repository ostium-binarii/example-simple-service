package reporting.model;

import java.util.Objects;


/**
 * Represents the stock ticker symbol for a company. While an Enum provides more type safety, this pojo was
 * chosen because it allows the java.service to support new companies if they are found while ingesting the source
 * data.
 */
public class CompanyCode {
    private static final String TICKER_CONVENTION = "^[A-Z:\\.0-9]+$";
    private final String code;

    public CompanyCode(final String code) {
        if (code == null || !code.matches(TICKER_CONVENTION)) {
            throw new IllegalArgumentException("Given company code " + code + " must follow convention " + TICKER_CONVENTION);
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyCode)) return false;
        CompanyCode that = (CompanyCode) o;
        return getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return code;
    }

}
