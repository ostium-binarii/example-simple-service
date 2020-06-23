package reporting.handler.dummy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reporting.model.ServiceResponse;

/**
 * This class is strictly used for unit testing the abstract "Handler" class.
 */
@RequiredArgsConstructor
@Getter
public class DummyResponse implements ServiceResponse {
    private final String testString;
}
