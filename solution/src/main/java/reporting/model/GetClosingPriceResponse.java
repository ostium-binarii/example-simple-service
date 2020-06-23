package reporting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Service level response for the GetClosingPrice API.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class GetClosingPriceResponse implements ServiceResponse {
    @NonNull private final CompanyCode companyCode;
    @NonNull private final Date date;
    private final BigDecimal price;
}
