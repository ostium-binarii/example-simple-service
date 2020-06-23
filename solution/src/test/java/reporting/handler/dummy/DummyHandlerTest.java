package reporting.handler.dummy;

import org.apache.commons.lang.NotImplementedException;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reporting.handler.Handler;
import reporting.model.Exception.BadRequestException;
import reporting.model.ServiceResponse;
import spark.Request;
import spark.Response;

import reporting.model.Exception.CompanyNotFoundException;
import reporting.model.UnsuccessfulResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


// Meant to test common functionality in the abstract "Handler" class so it won't need to be tested in concrete classes.
public class DummyHandlerTest {
    private Request mockRequest;
    private Response mockResponse;
    private Handler handlerUnderTest;

    @BeforeEach
    public void setUp() {
        mockRequest = mock(Request.class);
        mockResponse = mock(Response.class);
        handlerUnderTest = new DummyHandler();
    }

    @Test
    public void getPath_successFulInvocation() {
        //test
        String result = handlerUnderTest.getPath();
        //validate
        assertEquals(DummyHandler.TEST_PATH, result);
    }

    @Test
    public void handle_successFulInvocation() {
        //test
        DummyResponse result = (DummyResponse) handlerUnderTest.handle(mockRequest);
        //validate
        assertEquals(DummyHandler.TEST_RESPONSE, result.getTestString());
    }

    // note, this unit test is somewhat fragile as it depends on the message set by @NonNull. If the behavior
    // of that dependency changes, then this will break.
    @Test
    public void handle_nullRequest() {
        //arrange
        String npeMessage = "";
        //test
        try {
            handlerUnderTest.handle(null);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    @Test
    public void handleExceptionally_successFulInvocation() {
        //test
        ServiceResponse result = handlerUnderTest.handleExceptionally(mockRequest, mockResponse);
        //validate
        assertTrue(result instanceof DummyResponse);
    }

    @Test
    public void handleExceptionally_nullRequest() {
        //arrange
        String npeMessage = "";
        //test
        try {
            handlerUnderTest.handleExceptionally(null, mockResponse);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    @Test
    public void handleExceptionally_nullResponse() {
        //arrange
        String npeMessage = "";
        //test
        try {
            handlerUnderTest.handleExceptionally(mockRequest, null);
        } catch (NullPointerException e) {
            npeMessage = e.getMessage();
        }
        //validate
        assertTrue(npeMessage.contains("is marked non-null but is null"));
    }

    @Test
    public void handleExceptionally_badRequestException() {
        //arrange
        String expectedMessage = "expectedMessage";
        when(mockRequest.scheme()).thenThrow(new BadRequestException(expectedMessage));
        //test
        ServiceResponse result = handlerUnderTest.handleExceptionally(mockRequest, mockResponse);
        //validate
        verify(mockResponse).status(eq(HttpStatus.BAD_REQUEST_400));
        verify(mockResponse).body(eq(expectedMessage));
        assertTrue(result instanceof UnsuccessfulResponse);
    }

    @Test
    public void handleExceptionally_companyNotFoundException() {
        //arrange
        when(mockRequest.scheme()).thenThrow(new CompanyNotFoundException());
        //test
        ServiceResponse result = handlerUnderTest.handleExceptionally(mockRequest, mockResponse);
        //validate
        verify(mockResponse).status(eq(HttpStatus.NOT_FOUND_404));
        assertTrue(result instanceof UnsuccessfulResponse);
    }

    @Test
    public void handleExceptionally_notImplementedException() {
        //arrange
        when(mockRequest.scheme()).thenThrow(new NotImplementedException());
        //test
        ServiceResponse result = handlerUnderTest.handleExceptionally(mockRequest, mockResponse);
        //validate
        verify(mockResponse).status(eq(HttpStatus.NOT_IMPLEMENTED_501));
        assertTrue(result instanceof UnsuccessfulResponse);
    }

    @Test
    public void handleExceptionally_allOtherExceptions() {
        //arrange
        when(mockRequest.scheme()).thenThrow(new Exception());
        //test
        ServiceResponse result = handlerUnderTest.handleExceptionally(mockRequest, mockResponse);
        //validate
        verify(mockResponse).status(eq(HttpStatus.INTERNAL_SERVER_ERROR_500));
        assertTrue(result instanceof UnsuccessfulResponse);
    }

}
