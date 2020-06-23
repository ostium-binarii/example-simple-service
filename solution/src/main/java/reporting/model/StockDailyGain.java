package reporting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a company's daily % increase or decrease from the prior (available) stock closing price.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class StockDailyGain implements Comparable<StockDailyGain> {
    private final CompanyCode companyCode;
    private final Date date;
    private final BigDecimal closingPrice;
    private final BigDecimal percentGain;

    @Override
    public int compareTo(StockDailyGain o) {
        return percentGain.compareTo(o.getPercentGain());
    }
}
