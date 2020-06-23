package reporting.service;

import com.google.inject.Inject;
import lombok.NonNull;
import reporting.dao.DAO;
import reporting.dao.InMemoryDAO;
import reporting.model.GetAvgClosingPriceResponse;
import reporting.model.GetClosingPriceResponse;
import reporting.model.GetDatasetTimeRangeResponse;
import reporting.model.GetTopGainingResponse;
import reporting.model.StockDailyGain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import reporting.model.CompanyCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * Implementation of a reporting java.service for the FAANGFund exercise.
 * PLEASE NOTE: defensive copy is lacking here, but risk of a handler mutating data is rather low, so
 * it's necessity is arguable.
 */
public class FAANGFundReportingService implements Service {
    private static final Long YEAR_2100 = 4102448461L;
    private static final Long YEAR_1800 = -5364658739L;
    private static final int ROUNDING_PRECISION = 15;
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
     * @see InMemoryDAO#getClosingPrice(CompanyCode, Date)
     */
    @Override
    public GetClosingPriceResponse getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date) {
        BigDecimal closingPrice = dao.getClosingPrice(companyCode, date);
        return new GetClosingPriceResponse(companyCode, date, closingPrice);
    }

    /**
     * @see Service#getAvgClosingPrice(CompanyCode, Date, Date)
     * Including the operations in the DAO, this implementation is O(log n + m) where n is the total number of dates
     * for the given company (search for start and end dates in the tree of all dates) and m is the number of days
     * with data between the given start and end dates (loop over the sub-set). Some additional run time may be tacked
     * on from converting a SortedMap to Collection, but the exact amount is unknown.
     * @see InMemoryDAO#getClosingPrices(CompanyCode, Date, Date)
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
                ? sum.divide(BigDecimal.valueOf(numberOfDaysWithData), ROUNDING_PRECISION, RoundingMode.HALF_DOWN)
                : null;
        return new GetAvgClosingPriceResponse(companyCode, startDate, endDate, numberOfDaysWithData, avgClosingPrice);
    }

    /**
     * @see Service#getTopGaining()
     * Implementation run-time is O(n), where n is the number of all stock price closing dates in the entire dataset.
     * Because the intermediary TreeSet (red-black tree) is capped at a size of 10 elements, we maintain a constant
     * time for finding and tracking the top 10 elements. The expanded run-time would be O(n 2log(10)), where 10
     * is the max size of the tree set and 2 is the number of operations (add and poll) we do for each record (n).
     */
    @Override
    public GetTopGainingResponse getTopGaining() {
        Set<CompanyCode> companyCodes = dao.getCompanyCodes();
        // note TreeSet is not thread safe.
        TreeSet<StockDailyGain> topTenTracker = new TreeSet<>();

        for (CompanyCode companyCode : companyCodes) {
            TreeMap<Date,BigDecimal> closingPrices = dao.getClosingPrices(companyCode);
            BigDecimal lastPrice = closingPrices.pollFirstEntry().getValue();
            int dailyGainsMaxSize = 10;

            for (Map.Entry<Date,BigDecimal> closingPrice : closingPrices.entrySet()) {
                StockDailyGain stockDailyGain = processStockGain(companyCode, closingPrice, lastPrice);
                lastPrice = stockDailyGain.getClosingPrice();
                if (topTenTracker.size() < dailyGainsMaxSize) {
                    topTenTracker.add(stockDailyGain);
                } else {
                    topTenTracker.add(stockDailyGain);
                    topTenTracker.pollFirst();
                }
            }
        }

        List<StockDailyGain> topTenGains = new ArrayList<>(topTenTracker);
        Collections.reverse(topTenGains);

        return new GetTopGainingResponse(topTenGains);
    }

    private StockDailyGain processStockGain(
        final CompanyCode companyCode,
        final Map.Entry<Date,BigDecimal> closingPrice,
        final BigDecimal lastPrice
    ) {
        Date date = closingPrice.getKey();
        BigDecimal currentPrice = closingPrice.getValue();
        BigDecimal percentChange = currentPrice.subtract(lastPrice).divide(lastPrice, ROUNDING_PRECISION, RoundingMode.HALF_DOWN);
        return new StockDailyGain(companyCode, date, currentPrice, percentChange);
    }

}
