package handler;

import com.google.inject.Inject;
import model.GetAvgClosingPriceResponse;
import org.apache.commons.lang.NotImplementedException;
import service.Service;
import spark.Request;

/**
 * TODO: Not Implemented Yet.
 */
public class GetAvgClosingPriceHandler extends Handler {
    public static final String PATH = "/avg-closing-price";
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
        throw new NotImplementedException("Not implemented yet!");
    }

}
