package service;

import lombok.NonNull;
import model.CompanyCode;
import model.Exception.CompanyNotFoundException;
import model.GetAvgClosingPriceResponse;
import model.GetClosingPriceResponse;
import model.GetDatasetTimeRangeResponse;
import model.GetTopGainingResponse;

import java.util.Date;


/**
 * Service layer holding the business logic of the solution.
 */
public interface Service {
    /**
     * Returns the first and last dates in the available data.
     */
    GetDatasetTimeRangeResponse getDatasetTimeRange() throws CompanyNotFoundException;

    /**
     * Returns the stock closing price given a company code and closing date.
     */
    GetClosingPriceResponse getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date);

    /**
     * Returns the average of all stock closing prices from a specified start-date (inclusive) to an end-date (exclusive).
     */
    GetAvgClosingPriceResponse getAvgClosingPrice(
        @NonNull final CompanyCode companyCode,
        @NonNull final Date startDate,
        @NonNull final Date endDate
    );

    /**
     * TODO: NOT IMPLEMENTED.
     */
    GetTopGainingResponse getTopGaining();
}
