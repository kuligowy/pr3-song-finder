package pl.kuligowy.pr3sf.domain;

import lombok.*;

import java.time.*;

/**
 * Created by coolig on 27.06.17.
 */
@Data
public class SearchMarker {

    private final String title;
    private final LocalDate date;
    private final SearchMarkerStatus status;

    enum SearchMarkerStatus {
        STARTED,FINISHED
    }
}
