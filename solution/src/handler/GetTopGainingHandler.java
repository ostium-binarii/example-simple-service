package handler;

import com.google.inject.Inject;
import model.GetTopGainingResponse;
import org.apache.commons.lang.NotImplementedException;
import service.Service;
import spark.Request;


/**
 * TODO: Not Implemented Yet.
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
        throw new NotImplementedException("Not implemented yet!");
    }

}
