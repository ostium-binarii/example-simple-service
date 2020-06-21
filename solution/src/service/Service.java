package service;

import model.Exception.CompanyNotFoundException;
import model.GetAvgClosingPriceResponse;
import model.GetClosingPriceResponse;
import model.GetDatasetTimeRangeResponse;
import model.GetTopGainingResponse;


/**
 * Service layer holding the business logic of the solution.
 */
public interface Service {
    /**
     * Returns the first and last dates in the available data.
     */
    GetDatasetTimeRangeResponse getDatasetTimeRange() throws CompanyNotFoundException;

    /**
     * TODO: NOT IMPLEMENTED.
     */
    GetClosingPriceResponse getClosingPrice();

    /**
     * TODO: NOT IMPLEMENTED.
     */
    GetAvgClosingPriceResponse getAvgClosingPrice();

    /**
     * TODO: NOT IMPLEMENTED.
     */
    GetTopGainingResponse getTopGaining();
}
