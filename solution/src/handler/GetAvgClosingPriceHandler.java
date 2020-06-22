package handler;

import com.google.inject.Inject;
import datautilities.Marshaller;
import model.CompanyCode;
import model.Exception.BadRequestException;
import model.GetAvgClosingPriceResponse;
import service.Service;
import spark.Request;

import java.text.ParseException;
import java.util.Date;

/**
 * Handler for the GetAvgClosingPrice API. Returns the average price of all stock closing prices between a given
 * date range for a given company. Note the defined end day is excluded. If no stock closing prices could be found
 * for the requested company and date range (e.g. if requesting a date range before the company IPO'ed) then an
 * empt average price is returned (i.e. null).
 *
 * The API requires a request parameter representing company code (e.g. "AMZN" representing Amazon, note that this
 * is case insensitive) and two query parameters in format YYYY-MM-DD (e.g. "2001-01-22") for the start date
 * (inclusive) and end date (exclusive). A properly formatted request would look like:
 * curl "http://localhost:4567/reportingapi/avg-closing-price/amzn?startdate=2015-07-01&enddate=2015-08-01"
 *
 * The response should look like:
 * {"companyCode":{"code":"AMZN"},"startDate":"2015-07-01","endDate":"2015-08-01","numberOfDaysWithData":22,"avgPrice":478.709090909090909}
 */
public class GetAvgClosingPriceHandler extends Handler {
    private static final String requestParamCompanyCode = "companycode";
    private static final String requestQueryParamStartDate = "startdate";
    private static final String requestQueryParamEndDate = "enddate";
    public static final String PATH = "/avg-closing-price/:" + requestParamCompanyCode;
    private final Service service;

    @Inject
    public GetAvgClosingPriceHandler(final Service service) {
        this.service = service;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public GetAvgClosingPriceResponse handle(final Request request) {
        CompanyCode companyCode = null;
        Date startDate = null;
        Date endDate = null;

        try {
            companyCode = new CompanyCode(request.params(requestParamCompanyCode).toUpperCase());
            startDate = Marshaller.toDate(request.queryParams(requestQueryParamStartDate));
            endDate = Marshaller.toDate(request.queryParams(requestQueryParamEndDate));
        } catch (IllegalArgumentException | NullPointerException | ParseException e) {
            throw new BadRequestException(
                    "Bad request param and/or query params. Request Param companycode: " + request.params(requestParamCompanyCode)
                            + " Query Param startdate: " + request.queryParams(requestQueryParamStartDate)
                            + " Query Param enddate: " + request.queryParams(requestQueryParamStartDate)
            );
        }

        return service.getAvgClosingPrice(companyCode, startDate, endDate);
    }

}
