package dao;

import com.google.inject.Inject;
import datautilities.DataLoader;
import lombok.extern.slf4j.Slf4j;
import model.CompanyCode;
import model.Exception.CompanyNotFoundException;
import model.Exception.DataLoadException;
import org.apache.commons.lang.NotImplementedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Temporary DAO until a database is actually used. Loads data from a pre-configured data source into memory and
 * provides it for use by the service.
 */
@Slf4j
public class InMemoryDAO implements DAO {
    private final DataLoader dataLoader;
    // Note TreeMap is not thread safe, but all APIs are read only.
    private Map<CompanyCode,TreeMap<Date,BigDecimal>> stockMetrics;

    @Inject
    public InMemoryDAO(final DataLoader dataLoader) throws DataLoadException {
        this.dataLoader = dataLoader;
        refreshData();
    }

    /**
     * Loads data from the current sources to the instance.
     */
    private void refreshData() throws DataLoadException {
        stockMetrics = dataLoader.loadData();
    }

    /**
     * @see DAO#getCompanyCodes()
     */
    @Override
    public Set<CompanyCode> getCompanyCodes() {
        return stockMetrics.keySet();
    }

    /**
     * TODO: NOT IMPLEMENTED.
     */
    @Override
    public BigDecimal getClosingPrice(final CompanyCode companyCode, final Date startDate) {
        throw new NotImplementedException("THIS ISN'T CODED YET");
    }

    /**
     * @see dao.DAO#getClosingPrices(CompanyCode)
     */
    @Override
    public TreeMap<Date, BigDecimal> getClosingPrices(final CompanyCode companyCode) {
        TreeMap<Date, BigDecimal> closingPrices = stockMetrics.get(companyCode);
        if (closingPrices == null) {
            String message = "Company " + companyCode + " not found in closing prices data.";
            log.warn(message);
            throw new CompanyNotFoundException(message);
        }
        log.info("Found {} closing price records for company {} in the in-memory data loaded from {}.",
                closingPrices.size(), companyCode, dataLoader.getDataSource());
        return closingPrices;
    }

    /**
     * TODO: NOT IMPLEMENTED.
     */
    @Override
    public TreeMap<Date, BigDecimal> getClosingPrices(final CompanyCode companyCode, final Date startDate, final Date endDate) {
        throw new NotImplementedException("THIS ISN'T CODED YET");
    }

}
