package reporting.datautilities;

import reporting.model.Exception.DataLoadException;
import reporting.model.CompanyCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


/**
 * Loads data into memory from a data source. This is primarily for dependency injection.
 */
public interface DataLoader {
    /**
     * Returns a String representation of the data source of the loader.
     */
    String getDataSource();

    /**
     * Processes a data source into an in-memory data structure for use by the java.service.
     * @throws DataLoadException when an error is encountered processing the data source.
     */
    Map<CompanyCode,TreeMap<Date,BigDecimal>> loadData() throws DataLoadException;

}
