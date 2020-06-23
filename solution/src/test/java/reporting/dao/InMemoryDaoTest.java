package reporting.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reporting.model.Exception.DataLoadException;

import reporting.datautilities.DataLoader;
import java.math.BigDecimal;
import reporting.model.CompanyCode;
import reporting.model.Exception.CompanyNotFoundException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class InMemoryDaoTest {
    private static final CompanyCode AMZN = new CompanyCode("AMZN");
    private static final CompanyCode NON_EXSITING_CO = new CompanyCode("NEXC");
    private static final Date DATE_1999 = new Date(915152461);
    private static final Date NON_EXISTING_DATE = new Date(0);
    private static final BigDecimal PRICE_TEN = BigDecimal.TEN;
    private static final String DATA_SOURCE = "someFile.csv";

    private DataLoader mockDataLoader;
    private InMemoryDAO daoUnderTest;

    @BeforeEach
    public void setUp() throws DataLoadException {
        mockDataLoader = mock(DataLoader.class);
        when(mockDataLoader.loadData()).thenReturn(generateTestStockMetrics());
        daoUnderTest = new InMemoryDAO(mockDataLoader);
        when(mockDataLoader.getDataSource()).thenReturn(DATA_SOURCE);
    }

    @Test
    public void getClosingPrice_successfulInvocation(){
        //test
        BigDecimal result = daoUnderTest.getClosingPrice(AMZN, DATE_1999);
        //validate
        assertEquals(PRICE_TEN, result);
    }

    @Test
    public void getClosingPrice_noPriceForDate(){
        //test
        BigDecimal result = daoUnderTest.getClosingPrice(AMZN, NON_EXISTING_DATE);
        //validate
        assertNull(result);
    }

    @Test
    public void getClosingPrice_nonExistentCompanyCode() {
        //test
        assertThrows(
            CompanyNotFoundException.class,
            () -> daoUnderTest.getClosingPrice(NON_EXSITING_CO, DATE_1999)
        );
    }

    @Test
    public void getClosingPrice_nullCompanyCode() {
        //arrange
        String npeMessage = "";
        //test
        try {
            daoUnderTest.getClosingPrice(null, DATE_1999);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    @Test
    public void getClosingPrice_nullDate() {
        //arrange
        String npeMessage = "";
        //test
        try {
            daoUnderTest.getClosingPrice(AMZN, null);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    private Map<CompanyCode,TreeMap<Date,BigDecimal>> generateTestStockMetrics() {
        Map<CompanyCode,TreeMap<Date,BigDecimal>> stockMetrics = new HashMap<>();
        TreeMap<Date,BigDecimal> stockPrice = new TreeMap<>();
        stockPrice.put(DATE_1999, PRICE_TEN);
        stockMetrics.put(AMZN, stockPrice);
        return stockMetrics;
    }

}
