package reporting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


/**
 * Service level response for the GetTopGaining API.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class GetTopGainingResponse implements ServiceResponse {
    private final List<StockDailyGain> topTenStockGains;
}
