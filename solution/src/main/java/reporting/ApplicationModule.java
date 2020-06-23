package reporting;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import reporting.dao.InMemoryDAO;
import reporting.dao.DAO;
import reporting.datautilities.CsvFileLoader;
import reporting.datautilities.DataLoader;
import reporting.service.FAANGFundReportingService;
import reporting.service.Service;


/**
 * Module which defines dependency injection for the java.Application.
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
