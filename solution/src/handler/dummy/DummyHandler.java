package handler.dummy;

import com.google.common.annotations.VisibleForTesting;
import handler.Handler;
import lombok.NonNull;
import model.ServiceResponse;
import spark.Request;

/**
 * This class is strictly used for unit testing the abstract "Handler" class.
 */
@VisibleForTesting
public class DummyHandler extends Handler {
    public static final String TEST_PATH = "TEST_PATH";
    public static final String TEST_RESPONSE = "TEST_PATH";

    @Override
    public String getPath() {
        return TEST_PATH;
    }

    @Override
    public ServiceResponse handle(@NonNull Request request) {
        // invoking .scheme() to allow for mocking throwing Exceptions.
        request.scheme();
        return new DummyResponse(TEST_RESPONSE);
    }

}
