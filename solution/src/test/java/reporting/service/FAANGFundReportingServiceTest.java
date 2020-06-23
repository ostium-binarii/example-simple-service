package reporting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reporting.dao.DAO;
import reporting.model.GetClosingPriceResponse;

import java.math.BigDecimal;
import reporting.model.CompanyCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FAANGFundReportingServiceTest {
    private DAO mockDao;
    private FAANGFundReportingService serviceUnderTest;

    @BeforeEach
    public void setUp() {
        mockDao = mock(DAO.class);
        serviceUnderTest = new FAANGFundReportingService(mockDao);
    }

    @Test
    public void getClosingPrice_successfulInvocation() throws ParseException {
        //arrange
        CompanyCode companyCode = new CompanyCode("NFLX");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2009-05-06");
        when(mockDao.getClosingPrice(eq(companyCode), eq(date))).thenReturn(BigDecimal.TEN);
        //test
        GetClosingPriceResponse result = serviceUnderTest.getClosingPrice(companyCode, date);
        //validate
        GetClosingPriceResponse expectedResponse = new GetClosingPriceResponse(companyCode, date, BigDecimal.TEN);
        assertEquals(expectedResponse, result);
    }

    @Test
    public void getClosingPrice_nullCompanyCode() throws ParseException {
        //arrange
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2009-05-06");
        //test
        String npeMessage = "";
        try {
            serviceUnderTest.getClosingPrice(null, date);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    @Test
    public void getClosingPrice_nullDate() throws ParseException {
        //arrange
        CompanyCode companyCode = new CompanyCode("NFLX");
        //test
        String npeMessage = "";
        try {
            serviceUnderTest.getClosingPrice(companyCode, null);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

}
