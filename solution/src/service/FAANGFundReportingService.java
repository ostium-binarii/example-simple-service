package service;

import com.google.inject.Inject;
import dao.DAO;
import model.CompanyCode;
import model.GetAvgClosingPriceResponse;
import model.GetClosingPriceResponse;
import model.GetDatasetTimeRangeResponse;
import model.GetTopGainingResponse;
import org.apache.commons.lang.NotImplementedException;

import java.math.BigDecimal;
import java.time.Instant;
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
     * This implementation is O(m log(n)), where m is the number of companies in the entire dataSet and n is the
     * number of all available stock price closing dates in each company.
     */
    public GetDatasetTimeRangeResponse getDatasetTimeRange() {
        Set<CompanyCode> companyCodes = dao.getCompanyCodes();
        Date maxDate = Date.from(Instant.ofEpochSecond(YEAR_1800));
        Date minDate = Date.from(Instant.ofEpochSecond(YEAR_2100));

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

        return new GetDatasetTimeRangeResponse(maxDate, minDate);
    }

    /**
     * TODO: Not implemented yet.
     */
    @Override
    public GetClosingPriceResponse getClosingPrice() {
        throw new NotImplementedException("Not implemented yet!");
    }

    /**
     * TODO: Not implemented yet.
     */
    @Override
    public GetAvgClosingPriceResponse getAvgClosingPrice() {
        throw new NotImplementedException("Not implemented yet!");
    }

    /**
     * TODO: Not implemented yet.
     */
    @Override
    public GetTopGainingResponse getTopGaining() {
        throw new NotImplementedException("Not implemented yet!");
    }

}
