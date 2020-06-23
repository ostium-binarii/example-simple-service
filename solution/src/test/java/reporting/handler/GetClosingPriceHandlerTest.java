package reporting.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reporting.model.Exception.BadRequestException;
import reporting.model.GetClosingPriceResponse;
import reporting.service.Service;
import spark.Request;

import java.math.BigDecimal;
import reporting.model.CompanyCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetClosingPriceHandlerTest {
    // in real life we'd get these from something contractually obligates the handler to use them.
    private static final String PATH = "/closing-price/:companycode";
    private static final String REQUEST_PARAM = "companycode";
    private static final String REQUEST_QUERY_PARAM = "date";

    private Request mockRequest;
    private Service mockService;
    private GetClosingPriceHandler handlerUnderTest;

    @BeforeEach
    public void setUp() {
        mockRequest = mock(Request.class);
        mockService = mock(Service.class);
        handlerUnderTest = new GetClosingPriceHandler(mockService);
    }

    @Test
    public void getPath_successfulInvocation() {
        assertEquals(PATH, handlerUnderTest.getPath());
    }

    @Test
    public void handle_successfulInvocation() throws ParseException {
        //arrange for request parameter
        String companyCodeString = "amzn";
        when(mockRequest.params(eq(REQUEST_PARAM))).thenReturn(companyCodeString);
        //arrange for request query parameter
        String dateString = "2010-12-01";
        when(mockRequest.queryParams(eq(REQUEST_QUERY_PARAM))).thenReturn(dateString);
        //arrange for java.service
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        CompanyCode companyCode = new CompanyCode(companyCodeString.toUpperCase());
        GetClosingPriceResponse expectedResponse = new GetClosingPriceResponse(companyCode, date, BigDecimal.ZERO);
        when(mockService.getClosingPrice(eq(companyCode), eq(date))).thenReturn(expectedResponse);
        //test
        GetClosingPriceResponse result = handlerUnderTest.handle(mockRequest);
        //validate
        assertEquals(expectedResponse, result);
    }

    @Test
    public void handle_incorrectlyFormattedCompanyCode() {
        //arrange
        when(mockRequest.params(eq(REQUEST_PARAM))).thenReturn("NOT_RIGHT_CC_FORMAT$%^&*");
        when(mockRequest.queryParams(eq(REQUEST_QUERY_PARAM))).thenReturn("2021-09-30");
        //test
        assertThrows(BadRequestException.class, () -> handlerUnderTest.handle(mockRequest));
    }

    @Test
    public void handle_noCompanyCode() {
        //arrange
        when(mockRequest.params(eq(REQUEST_PARAM))).thenReturn(null);
        when(mockRequest.queryParams(eq(REQUEST_QUERY_PARAM))).thenReturn("1986-10-15");
        //test
        assertThrows(BadRequestException.class, () -> handlerUnderTest.handle(mockRequest));
    }

    @Test
    public void handle_incorrectlyFormattedDate() {
        //arrange
        when(mockRequest.params(eq(REQUEST_PARAM))).thenReturn("fb");
        when(mockRequest.queryParams(eq(REQUEST_QUERY_PARAM))).thenReturn("GOBBLE_DY_GOOK");
        //test
        assertThrows(BadRequestException.class, () -> handlerUnderTest.handle(mockRequest));
    }

    @Test
    public void handle_noDate() {
        //arrange
        when(mockRequest.params(eq(REQUEST_PARAM))).thenReturn("goog");
        when(mockRequest.queryParams(eq(REQUEST_QUERY_PARAM))).thenReturn(null);
        //test
        assertThrows(BadRequestException.class, () -> handlerUnderTest.handle(mockRequest));
    }

}
