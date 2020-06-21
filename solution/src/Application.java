import com.google.inject.Guice;
import com.google.inject.Injector;
import datautilities.Marshaller;
import handler.GetAvgClosingPriceHandler;
import handler.GetClosingPriceHandler;
import handler.GetDatasetTimeRangeHandler;
import handler.GetTopGainingHandler;
import handler.Handler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static spark.Spark.get;


/**
 * Simple application that starts a lightweight web server on the local host with FAANGFundReportingService APIs.
 */
@Slf4j
public class Application {
    /**
     * Entry-point when running the application in a stand-alone manner (e.g. from an IDE or the command-line).
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        new Application().init();
    }

    /**
     * Initializes the web server with predefined configurations. The following four endpoints should be available
     * upon successful initialization:
     * http://localhost:4567/dataset-time-range
     * http://localhost:4567/closing-price
     * http://localhost:4567/avg-closing-price
     * http://localhost:4567/top-gaining
     */
    public void init() {
        getConfiguredHandlers().forEach(
            handler -> get(
                handler.getPath(),
                (request, response) -> Marshaller.toString(handler.handleExceptionally(request, response))
            )
        );
        // TODO: configure global log tags with host and service name.
        log.info("Application successfully initialized.");
    }

    /**
     * Provides each APIs handler with configured dependencies (i.e. specific Service/DAO implementations).
     * @return instances of API handlers with all necessary dependencies.
     */
    private List<Handler> getConfiguredHandlers() {
        final Injector injector = Guice.createInjector(new ApplicationModule());

        // bindings ensure only one Service and DAO instance are used across all handlers.
        return Arrays.asList(
            injector.getInstance(GetDatasetTimeRangeHandler.class),
            injector.getInstance(GetClosingPriceHandler.class),
            injector.getInstance(GetAvgClosingPriceHandler.class),
            injector.getInstance(GetTopGainingHandler.class)
        );
    }

}
