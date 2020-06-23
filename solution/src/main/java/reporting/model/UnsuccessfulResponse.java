package reporting.model;

/**
 * Generic response for when the Service was unable to fulfill a request for whatever reason.
 */
public class UnsuccessfulResponse implements ServiceResponse {
    private final String message =
        "Never gonna give you up\n" +
        "Never gonna let you down\n" +
        "Never gonna run around and desert you\n" +
        "Never gonna make you cry\n" +
        "Never gonna say goodbye\n" +
        "Never gonna tell a lie and hurt you";

    public String getMessage() {
        return message;
    }

}
