package pl.kuligowy.pr3sf.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DailyBroadcastCollection {

    private LocalDateTime updated;
    private List<Broadcast> broadcastList;
}
