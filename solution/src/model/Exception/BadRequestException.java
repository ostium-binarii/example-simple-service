package model.Exception;

/**
 * Thrown to indicate that the request from the client passed on to a handler does not follow expected standards.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final Exception cause) {
        super(cause);
    }

    public BadRequestException(final String message, final Exception cause) {
        super(message, cause);
    }

}
