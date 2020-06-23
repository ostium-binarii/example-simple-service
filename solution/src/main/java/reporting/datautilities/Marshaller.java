package reporting.datautilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Handles marshalling and unmarshalling data at, mainly, ingress and egress points of the service (e.g. transforms pojos
 * into json strings before sending to clients, or converts consumed data from files into appropriate Java objects).
 */
public class Marshaller {
    // Date format should really come from a source that contractually obligates senders/receivers on both sides.
    private static final SimpleDateFormat YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");

    // Util class does not require instantiation.
    private Marshaller() {}

    /**
     * Converts POJOs (plain old java objects) into JSON Strings.
     */
    public static String toString(final Object pojo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(YEAR_MONTH_DAY);
        return mapper.writeValueAsString(pojo);
    }

    /**
     * Converts String representations of Dates to Java Date objects.
     */
    public static Date toDate(final String yearMonthDay) throws ParseException {
        return YEAR_MONTH_DAY.parse(yearMonthDay);
    }

    /**
     * Converts Java Date objects into human readable Strings.
     */
    public static String toReadableDate(final Date date) {
        return YEAR_MONTH_DAY.format(date);
    }

}
