package reporting.handler;

import com.google.inject.Inject;
import reporting.model.GetTopGainingResponse;
import reporting.service.Service;
import spark.Request;


/**
 * Handler for the GetDataSetTimeRange API. When invoked returns the first and last stock price closing dates in the
 * available data.
 *
 * The API requires no arguments/data other than a request to the appropriate endpoint, like so:
 * curl http://localhost:4567/reportingapi/top-gaining
 *
 * The response should look like:
 * {
 *  "topTenStockGains":
 *      [
 *       {"companyCode":{"code":"NFLX"},"date":"2013-01-24","closingPrice":20.98,"percentGain":0.422372881355932},
 *       {"companyCode":{"code":"NFLX"},"date":"2002-10-10","closingPrice":0.51,"percentGain":0.378378378378378},
 *       {"companyCode":{"code":"AMZN"},"date":"2001-11-26","closingPrice":12.21,"percentGain":0.344713656387665},
 *       {"companyCode":{"code":"AMZN"},"date":"2001-04-09","closingPrice":11.18,"percentGain":0.335722819593787},
 *       {"companyCode":{"code":"AAPL"},"date":"1997-08-06","closingPrice":0.94,"percentGain":0.323943661971831},
 *       {"companyCode":{"code":"AMZN"},"date":"2001-11-14","closingPrice":9.49,"percentGain":0.301783264746228},
 *       {"companyCode":{"code":"FB"},"date":"2013-07-25","closingPrice":34.36,"percentGain":0.296114673708035},
 *       {"companyCode":{"code":"AMZN"},"date":"2007-04-25","closingPrice":56.81,"percentGain":0.269497206703911},
 *       {"companyCode":{"code":"AMZN"},"date":"2009-10-23","closingPrice":118.49,"percentGain":0.267950775815944},
 *       {"companyCode":{"code":"AMZN"},"date":"2001-01-03","closingPrice":17.56,"percentGain":0.265129682997118}
 *      ]
 * }
 */
public class GetTopGainingHandler extends Handler {
    public static final String PATH = "/top-gaining";
    private final Service service;

    @Inject
    public GetTopGainingHandler(final Service service) {
        this.service = service;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public GetTopGainingResponse handle(final Request request) {
        return service.getTopGaining();
    }

}
