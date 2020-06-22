package model.Exception;

/**
 * Thrown when a company cannot be found in the database upon a request for it.
 */
public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super();
    }

    public CompanyNotFoundException(final String message) {
        super(message);
    }

    public CompanyNotFoundException(final Exception cause) {
        super(cause);
    }

    public CompanyNotFoundException(final String message, final Exception cause) {
        super(message, cause);
    }

}
