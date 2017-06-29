package pl.kuligowy.pr3sf.respositories;

import org.springframework.data.jpa.domain.*;

import java.time.*;

/**
 * Created by coolig on 28.06.17.
 */
public class BroadcastSpec {

    public static Specification getForDay(LocalDate day){
        LocalDateTime dateX = LocalDateTime.of(day, LocalTime.MIN);
        LocalDateTime dateY = LocalDateTime.of(day, LocalTime.MAX);
       return (root, query, cb) -> cb.between(root.get("start"),dateX,dateY);
    }
    public static Specification getForDayAndBroadcast(LocalDate day,long broadcastId){
        LocalDateTime dateX = LocalDateTime.of(day, LocalTime.MIN);
        LocalDateTime dateY = LocalDateTime.of(day, LocalTime.MAX);
       return (root, query, cb) -> cb.and(
               cb.between(root.get("start"),dateX,dateY),
               cb.equal(root.get("id"),broadcastId)
       );
    }
}
