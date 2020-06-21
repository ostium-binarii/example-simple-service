package handler;

import com.google.inject.Inject;
import model.GetClosingPriceResponse;
import org.apache.commons.lang.NotImplementedException;
import service.Service;
import spark.Request;
import spark.Response;


/**
 * TODO: Not Implemented Yet.
 */
public class GetClosingPriceHandler extends Handler {
    public static String PATH = "/closing-price";
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
    public GetClosingPriceResponse handle(final Request request, final Response response) {
        throw new NotImplementedException("Not implemented yet!");
    }

}
