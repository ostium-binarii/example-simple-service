package service;

import com.google.inject.Inject;
import dao.DAO;
import lombok.NonNull;
import model.CompanyCode;
import model.GetAvgClosingPriceResponse;
import model.GetClosingPriceResponse;
import model.GetDatasetTimeRangeResponse;
import model.GetTopGainingResponse;
import org.apache.commons.lang.NotImplementedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;


/**
 * Implementation of a reporting service for the FAANGFund exercise.
 */
public class FAANGFundReportingService implements Service {
    private static final Long YEAR_2100 = 4102448461L;
    private static final Long YEAR_1800 = -5364658739L;
    private final DAO dao;

    @Inject
    public FAANGFundReportingService(final DAO dao) {
        this.dao = dao;
    }

    /**
     * @see Service#getDatasetTimeRange()
     * This implementation is O(m log(n)), where m is the number of companies in the entire data set and n is the
     * number of all available stock price closing dates in each company.
     */
    public GetDatasetTimeRangeResponse getDatasetTimeRange() {
        Set<CompanyCode> companyCodes = dao.getCompanyCodes();
        Date maxDate = Date.from(Instant.ofEpochSecond(YEAR_1800));
        Date minDate = Date.from(Instant.ofEpochSecond(YEAR_2100));

        // while impossible with the current data, an edge case to think about would be if all companies were
        // completely new and had no stock price closing dates. The min/max dates would be of year 1800/2100
        for (CompanyCode companyCode : companyCodes) {
            TreeMap<Date, BigDecimal> companyClosingPrices = dao.getClosingPrices(companyCode);
            Date companyMaxDate = companyClosingPrices.lastKey();
            Date companyMinDate = companyClosingPrices.firstKey();

            if (maxDate.compareTo(companyMaxDate) < 0) {
                maxDate = companyMaxDate;
            }
            if (minDate.compareTo(companyMinDate) > 0) {
                minDate = companyMinDate;
            }
        }

        return new GetDatasetTimeRangeResponse(minDate, maxDate);
    }

    /**
     * @see Service#getClosingPrice(CompanyCode, Date)
     * Including the operations in the DAO, this implementation is O(log(n)) where n is the number of all stock price
     * closing dates for a given company:
     * @see dao.InMemoryDAO#getClosingPrice(CompanyCode, Date) 
     */
    @Override
    public GetClosingPriceResponse getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date) {
        BigDecimal closingPrice = dao.getClosingPrice(companyCode, date);
        return new GetClosingPriceResponse(companyCode, date, closingPrice);
    }

    /**
     * @see Service#getAvgClosingPrice(CompanyCode, Date, Date)
     * Inclduing the operations in the DAO, this implementation is O(log n + m) where n is the total number of dates
     * for the given company and m is the number of days with data between the given start and end dates. Some 
     * additional run time is tacked on from converting a SortedMap to Collection, but the exact amount is unknown.
     * @see dao.InMemoryDAO#getClosingPrices(CompanyCode, Date, Date)
     */
    @Override
    public GetAvgClosingPriceResponse getAvgClosingPrice(
        @NonNull final CompanyCode companyCode,
        @NonNull final Date startDate,
        @NonNull final Date endDate
    ) {
        Collection<BigDecimal> closingPrices = dao.getClosingPrices(companyCode, startDate, endDate);
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal closingPrice : closingPrices) {
            sum = sum.add(closingPrice);
        }
        long numberOfDaysWithData = closingPrices.size();
        BigDecimal avgClosingPrice = (numberOfDaysWithData > 0)
                ? sum.divide(BigDecimal.valueOf(numberOfDaysWithData), RoundingMode.HALF_DOWN)
                : null;
        return new GetAvgClosingPriceResponse(companyCode, startDate, endDate, numberOfDaysWithData, avgClosingPrice);
    }

    /**
     * TODO: Not implemented yet.
     */
    @Override
    public GetTopGainingResponse getTopGaining() {
        throw new NotImplementedException("Not implemented yet!");
    }

}
