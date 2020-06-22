package handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.Exception.BadRequestException;
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
     * Defines the API's path (e.g. if the service was hosted at "http://localhost:4567/reportingapi" and this method
     * returned "/imateapot" then the API's endpoint would be "http://localhost:4567/reportingapi/imateapot")
     */
    public abstract String getPath();

    /**
     * Handles requests to API.
     */
    abstract ServiceResponse handle(@NonNull final Request request);

    /**
     * Handles request to APIs further applying common rules, such as global exception handling and logging.
     * PLEASE NOTE: in the interest of time, the code below is not production standard in terms of things like
     * handling bad requests, logging, and responding well to the client. For example, per request, we'd be
     * logging a lot more than the API, host, and ip-- at the very least we'd log the parameters, query
     * parameters, payload, etc.
     */
    // normally we'd check if the request is null and if so, return a 400, but we're relying on the Spark API framework
    // to handle this. If @NonNull here throws an NPE we know something has likely gone wrong with Spark.
    public ServiceResponse handleExceptionally(@NonNull final Request request, @NonNull final Response response) {
        String handlerName = getClass().getSimpleName();
        log.info("Received request to API {}: host:{} ip:{} etc.", handlerName, request.host(), request.ip());

        try {
            return handle(request);
        } catch (BadRequestException e) {
            log.warn("Bad request to API handler {}", handlerName, e);
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(e.getMessage());
        } catch (CompanyNotFoundException e) {
            log.warn("Company was not found during request to API handler {}", handlerName, e);
            response.status(HttpStatus.NOT_FOUND_404);
            response.body("The company you have requested does not exist!");
        } catch (NotImplementedException e) {
            log.info("The in-development feature {} was requested. Check feature demand.", handlerName, e);
            response.status(HttpStatus.NOT_IMPLEMENTED_501);
            response.body("Sorry, we're still working on this!");
        } catch (Exception e) {
            log.error("Request to API handler {} failed.", handlerName, e);
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body("Sorry Something Went Wrong!");
        }

        // note that this is simply a dummy object not to be used by the front-end in case of failure. If a failure
        // occurs (e.g. 500 error) the front-end should rely on the status/body set on the Spark Response instance.
        return new UnsuccessfulResponse();
    }

}
