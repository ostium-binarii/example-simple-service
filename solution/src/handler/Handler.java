package handler;

import lombok.extern.slf4j.Slf4j;
import model.Exception.CompanyNotFoundException;
import model.ServiceResponse;
import model.UnsuccessfulResponse;
import org.apache.commons.lang.NotImplementedException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

/**
 * API Handler.
 */
@Slf4j
public abstract class Handler {
    /**
     * Defines the API's path (e.g. if the service was hosted at "http://localhost:4567" and this method
     * returned "/imateapot" then the API's endpoint would be "http://localhost:4567/imateapot")
     */
    public abstract String getPath();

    /**
     * Handles requests to API.
     */
    abstract ServiceResponse handle(final Request request, final Response response);

    /**
     * Handles request to APIs further applying common rules, such as global exception handling and logging.
     */
    public ServiceResponse handleExceptionally(final Request request, final Response response) {
        // in reality we'd log a lot more than just the below elements of the request.
        log.info("Received request: host:{} ip:{} port:{} etc.", request.host(), request.ip(), request.port());

        try {
            return handle(request, response);
        } catch (CompanyNotFoundException e) {
            log.warn("Data was not found during request to feature {}", getPath());
            response.status(HttpStatus.NOT_FOUND_404);
            response.body("The data you have requested does not exist!");
        } catch (NotImplementedException e) {
            log.info("The in-development feature {} was requested. Check feature demand.", getPath());
            response.status(HttpStatus.NOT_IMPLEMENTED_501);
            response.body("Sorry, we're still working on this!");
        } catch (Exception e) {
            // in real life we'd log all the things that could make the request go bad, not just the params.
            log.error("Request with params {} for feature {} failed.", request.params(), getPath(), e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body("Sorry Something Went Wrong!");
        }

        return new UnsuccessfulResponse();
    }

}
