package pl.kuligowy.pr3sf.domain;

import lombok.*;

import java.time.*;

/**
 * Created by coolig on 28.06.17.
 */
@Data
public class ProgressMarker {

    private String message;
    private LocalDateTime start;
    private StateMarker state;

}
