package model.Exception;

/**
 * Indicates when loading reporting data to the service has failed. This could be while loading a csv file to
 * memory, or when refreshing a cache from the database.
 */
public class DataLoadException extends Exception {

    public DataLoadException() {
        super();
    }

    public DataLoadException(final String message) {
        super(message);
    }

    public DataLoadException(final Exception cause) {
        super(cause);
    }

    public DataLoadException(final String message, final Exception cause) {
        super(message, cause);
    }

}
