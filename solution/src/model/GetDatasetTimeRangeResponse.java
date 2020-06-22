package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;


/**
 * Service level response for the GetDatasetTimeRange API.
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class GetDatasetTimeRangeResponse implements ServiceResponse {
    private final Date minDate;
    private final Date maxDate;
}
