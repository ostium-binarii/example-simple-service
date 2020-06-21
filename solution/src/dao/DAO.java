package dao;

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
     * TODO: NOT IMPLEMENTED.
     */
    BigDecimal getClosingPrice(final CompanyCode companyCode, final Date startDate);

    /**
     * Returns the closing prices for all available dates for a given company.
     */
    TreeMap<Date, BigDecimal> getClosingPrices(final CompanyCode companyCode);

    /**
     * TODO: NOT IMPLEMENTED.
     */
    TreeMap<Date, BigDecimal> getClosingPrices(final CompanyCode companyCode, final Date startDate, final Date endDate);

}
