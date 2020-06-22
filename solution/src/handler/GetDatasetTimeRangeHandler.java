package handler;

import com.google.inject.Inject;
import lombok.NonNull;
import model.GetDatasetTimeRangeResponse;
import service.Service;
import spark.Request;


/**
 * Handler for the GetDataSetTimeRange API. When invoked returns the first and last stock price closing dates in the
 * available data.
 *
 * The API requires no arguments/data other than a request to the appropriate endpoint, like so:
 * curl http://localhost:4567/reportingapi/dataset-time-range
 *
 * The response should look like:
 * {"minDate":"1989-09-19","maxDate":"2019-11-15"}
 *
 * PLEASE NOTE: Currently no handling is done in cases where there is no data at all, or if none of the companies in
 * available data have any stock price clsoing dates.
 */
public class GetDatasetTimeRangeHandler extends Handler {
    private static final String PATH = "/dataset-time-range";
    private final Service service;

    @Inject
    public GetDatasetTimeRangeHandler(final Service service) {
        this.service = service;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public GetDatasetTimeRangeResponse handle(@NonNull final Request request) {
        return service.getDatasetTimeRange();
    }

}
