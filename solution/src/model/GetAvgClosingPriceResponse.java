package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Service level response for the GetAvgClosingPrice API.
 */
@RequiredArgsConstructor
@Getter
public class GetAvgClosingPriceResponse implements ServiceResponse {
    @NonNull private final CompanyCode companyCode;
    @NonNull private final Date startDate;
    @NonNull private final Date endDate;
    @NonNull private final Long numberOfDaysWithData;
    private final BigDecimal avgPrice;
}
