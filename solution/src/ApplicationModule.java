import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import dao.InMemoryDAO;
import dao.DAO;
import datautilities.CsvFileLoader;
import datautilities.DataLoader;
import service.FAANGFundReportingService;
import service.Service;


/**
 * Module which defines dependency injection for the Application.
 */
public class ApplicationModule extends AbstractModule {
    /**
     * Sets which specific implementations of interfaces should be injected at run-time
     * and where/under what conditions they should injected.
     */
    @Override
    protected void configure() {
        bind(Service.class).to(FAANGFundReportingService.class).in(Singleton.class);
        bind(DAO.class).to(InMemoryDAO.class).in(Singleton.class);
        bind(DataLoader.class).to(CsvFileLoader.class).in(Singleton.class);
    }

}
