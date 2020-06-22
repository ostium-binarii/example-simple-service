package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;


/**
 * Service level response for the GetDatasetTimeRange API.
 */
@RequiredArgsConstructor
@Getter
public class GetDatasetTimeRangeResponse implements ServiceResponse {
    private final Date minDate;
    private final Date maxDate;
}
