package dao;

import lombok.NonNull;
import model.CompanyCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;


/**
 * Data Access Object providing raw data for use by the service.
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
     */
    TreeMap<Date, BigDecimal> getClosingPrices(@NonNull final CompanyCode companyCode);

    /**
     * TODO: NOT IMPLEMENTED.
     */
    TreeMap<Date, BigDecimal> getClosingPrices(@NonNull final CompanyCode companyCode, @NonNull final Date startDate, final Date endDate);

}
