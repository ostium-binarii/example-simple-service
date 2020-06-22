package dao;

import com.google.inject.Inject;
import datautilities.DataLoader;
import datautilities.Marshaller;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import model.CompanyCode;
import model.Exception.CompanyNotFoundException;
import model.Exception.DataLoadException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * Temporary DAO until a database is actually used. Loads data from a pre-configured data source into memory and
 * provides it for use by the service.
 * PLEASE NOTE: some elements of thread safety and defensive copying are lacking in this class, leaving as-is for
 * the sake of time.
 */
@Slf4j
public class InMemoryDAO implements DAO {
    private final DataLoader dataLoader;
    // Note TreeMap is not thread safe.
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
     * PLEASE NOTE: in the interest of time, this only performs a shallow copy as opposed to a deep copy when
     * returning the data.
     */
    @Override
    public Set<CompanyCode> getCompanyCodes() {
        return new HashSet<>(stockMetrics.keySet());
    }

    /**
     * @see DAO#getClosingPrice(CompanyCode, Date)
     */
    @Override
    public BigDecimal getClosingPrice(@NonNull final CompanyCode companyCode, @NonNull final Date date) {
        checkExists(companyCode);
        BigDecimal closingPrice = stockMetrics.get(companyCode).get(date);
        log.info("Closing price for company {} was {} on {} from source: in-memory data loaded from {}.",
                companyCode, closingPrice, Marshaller.toReadableDate(date), dataLoader.getDataSource());
        return closingPrice;
    }

    /**
     * @see dao.DAO#getClosingPrices(CompanyCode)
     * PLEASE NOTE: in the interest of time, this only performs a shallow copy as opposed to a deep copy when
     * returning the data. While BigDecimal instances are thread safe, note that Date is mutable.
     */
    @Override
    public TreeMap<Date, BigDecimal> getClosingPrices(@NonNull final CompanyCode companyCode) {
        checkExists(companyCode);
        TreeMap<Date, BigDecimal> closingPrices = stockMetrics.get(companyCode);
        log.info("Found {} closing price records for company {} from source: in-memory data loaded from {}.",
                closingPrices.size(), companyCode, dataLoader.getDataSource());
        return new TreeMap<>(closingPrices);
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
        log.info("Found {} closing price records for company {} between {} and {} from source: in-memory data loaded from {}.",
                closingPrices.size(), companyCode, Marshaller.toReadableDate(startDate), Marshaller.toReadableDate(endDate), dataLoader.getDataSource());
        return closingPrices;
    }

    private void checkExists(final CompanyCode companyCode) {
        if (!stockMetrics.containsKey(companyCode)) {
            String message = "CompanyCode " + companyCode + " not found in data loaded to the InMemoryDao from " + dataLoader.getDataSource();
            log.error(message);
            throw new CompanyNotFoundException(message);
        }
    }

}
