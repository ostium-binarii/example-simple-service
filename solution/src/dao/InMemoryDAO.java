package dao;

import com.google.inject.Inject;
import datautilities.DataLoader;
import datautilities.Marshaller;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.CompanyCode;
import model.Exception.DataLoadException;

import java.math.BigDecimal;
import java.util.Collection;
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
     * @see DAO#getClosingPrice(CompanyCode, Date)
     */
    @Override
    public BigDecimal getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date) {
        checkExists(companyCode);
        BigDecimal closingPrice = stockMetrics.get(companyCode).get(date);
        log.debug("Closing price for company {} was {} on {} from source: in-memory data loaded from {}.",
                companyCode, closingPrice, Marshaller.toReadableDate(date), dataLoader.getDataSource());
        return closingPrice;
    }

    /**
     * @see dao.DAO#getClosingPrices(CompanyCode)
     */
    @Override
    public TreeMap<Date, BigDecimal> getClosingPrices(@NonNull final CompanyCode companyCode) {
        checkExists(companyCode);
        TreeMap<Date, BigDecimal> closingPrices = stockMetrics.get(companyCode);
        log.debug("Found {} closing price records for company {} from source: in-memory data loaded from {}.",
                closingPrices.size(), companyCode, dataLoader.getDataSource());
        return closingPrices;
    }

    /**
     * @see DAO#getClosingPrices(CompanyCode, Date, Date)
     */
    @Override
    public Collection<BigDecimal> getClosingPrices(
        @NonNull final CompanyCode companyCode,
        @NonNull final Date startDate,
        @NonNull final Date endDate
    ) {
        checkExists(companyCode);
        Collection<BigDecimal> closingPrices = stockMetrics.get(companyCode).subMap(startDate, endDate).values();
        log.debug("Found {} closing price records for company {} between {} and {} from source: in-memory data loaded from {}.",
                closingPrices.size(), companyCode, Marshaller.toReadableDate(startDate), Marshaller.toReadableDate(endDate), dataLoader.getDataSource());
        return closingPrices;
    }

    private void checkExists(final CompanyCode companyCode) {
        if (companyCode == null) {
            String message = "Given CompanyCode instance was null at the DAO!";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

}
