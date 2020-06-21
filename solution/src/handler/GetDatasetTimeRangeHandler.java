package handler;

import com.google.inject.Inject;
import model.GetDatasetTimeRangeResponse;
import service.Service;
import spark.Request;
import spark.Response;


/**
 * Handler for the GetDataSetTimeRange API.
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
    public GetDatasetTimeRangeResponse handle(final Request request, final Response response) {
        return service.getDatasetTimeRange();
    }

}
