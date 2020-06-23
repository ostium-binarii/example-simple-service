package reporting.dao;

import lombok.NonNull;
import reporting.model.CompanyCode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;


/**
 * Data Access Object providing raw data for use by the java.service.
 */
public interface DAO {
    /**
     * Returns all distinct company codes (e.g. AAPL, GOOG) from the dataset.
     */
    Set<CompanyCode> getCompanyCodes();

    /**
     * Returns the stock closing price given a company code and closing date.
     */
    BigDecimal getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date);

    /**
     * Returns the closing prices for all available dates for a given company.
     *
     * PLEASE NOTE: if we ever switched over to something like DynamoDB in the future, and decided to take
     * advantage of GSIs/LSIs for performance instead of the in-memory data structures we chose in this
     * project, the decision to return TreeMap here will probably make things harder to refactor.
     */
    TreeMap<Date, BigDecimal> getClosingPrices(@NonNull final CompanyCode companyCode);

    /**
     * Returns the closing prices for a given company from a specified start-date (inclusive) to an end-date (exclusive).
     */
    Collection<BigDecimal> getClosingPrices(
        @NonNull final CompanyCode companyCode,
        @NonNull final Date startDate,
        @NonNull final Date endDate
    );

}
