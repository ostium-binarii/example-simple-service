package reporting.handler;

import com.google.inject.Inject;
import lombok.NonNull;
import reporting.datautilities.Marshaller;
import reporting.model.Exception.BadRequestException;
import reporting.model.GetClosingPriceResponse;
import reporting.service.Service;
import spark.Request;

import reporting.model.CompanyCode;

import java.text.ParseException;
import java.util.Date;


/**
 * Handler for the GetClosingPrice API. When invoked, returns the closing price for a given company code and date. If
 * the stock market was closed on the requested date, a future date is requested, or the requested company was not
 * publically traded on the requested date, this will return and empty price (i.e. null).
 *
 * The API requires a request parameter representing company code (e.g. "AMZN" representing Amazon, note that this
 * is case insensitive) and a query parameter in format YYYY-MM-DD (e.g. "2001-01-22"). A properly formatted request
 * would look like:
 * curl "http://localhost:4567/reportingapi/closing-price/nflx?date=2019-09-09"
 *
 * The response should look like:
 * {"companyCode":{"code":"NFLX"},"date":"2019-09-09","price":294.34}
 *
 * PLEASE NOTE: While not currently coded to do so, in my opinion, we should return a 404 when a closing day is not
 * found when we actually expect it, but return null when the closing day is legitimately missing. For example, we
 * would return null price for Facebook, 2015-01-01 because the market is closed on New Year's day, but we'd 404
 * on 2015-01-02.
 */
public class GetClosingPriceHandler extends Handler {
    private static final String requestParamCompanyCode = "companycode";
    private static final String requestQueryParamDate = "date";
    public static final String PATH = "/closing-price/:" + requestParamCompanyCode;

    private final Service service;

    @Inject
    public GetClosingPriceHandler(final Service service) {
        this.service = service;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected GetClosingPriceResponse handle(@NonNull final Request request) {
        CompanyCode companyCode = null;
        Date date = null;

        try {
            companyCode = new CompanyCode(request.params(requestParamCompanyCode).toUpperCase());
            date = Marshaller.toDate(request.queryParams(requestQueryParamDate));
        } catch (IllegalArgumentException | NullPointerException | ParseException e) {
            throw new BadRequestException(
                "Bad request param and/or query param. Request Param companycode: " + request.params(requestParamCompanyCode)
                + " Query Param date: " + request.queryParams(requestQueryParamDate)
            );
        }

        return service.getClosingPrice(companyCode, date);
    }

}
