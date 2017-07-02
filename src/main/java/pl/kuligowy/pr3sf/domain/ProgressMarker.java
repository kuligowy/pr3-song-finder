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


    public static ProgressMarker STARTED(){
        ProgressMarker marker = new ProgressMarker();
        marker.setState(StateMarker.STARTED);
        marker.setStart(LocalDateTime.now());
        marker.setMessage("Started fetching new entries...");
        return marker;
    }

    public static ProgressMarker FETCHING(){
        ProgressMarker marker = new ProgressMarker();
        marker.setState(StateMarker.FETCHING);
        marker.setStart(LocalDateTime.now());
        marker.setMessage("Still fetching new entries...");
        return marker;
    }

    public static ProgressMarker FINISHED(){
        ProgressMarker marker = new ProgressMarker();
        marker.setState(StateMarker.FINISHED);
        marker.setStart(LocalDateTime.now());
        marker.setMessage("Finished fetching new entries.");
        return marker;
    }
}
