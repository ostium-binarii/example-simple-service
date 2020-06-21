package model;

import java.util.Objects;


/**
 * Represents the stock ticker symbol for a company. While an Enum provides more type safety, this pojo was
 * chosen because it allows the service to support new companies if they are found while ingesting the source
 * data.
 */
public class CompanyCode {
    private static final String TICKER_CONVENTION = "^[A-Z:\\.0-9]+$";
    private final String companyCode;

    public CompanyCode(final String companyCode) {
        if (!companyCode.matches(TICKER_CONVENTION)) {
            throw new IllegalArgumentException("C");
        }
        this.companyCode = companyCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyCode)) return false;
        CompanyCode that = (CompanyCode) o;
        return getCompanyCode().equals(that.getCompanyCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyCode());
    }

    @Override
    public String toString() {
        return companyCode;
    }

}
