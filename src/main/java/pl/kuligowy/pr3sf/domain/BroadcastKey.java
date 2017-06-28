package pl.kuligowy.pr3sf.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BroadcastKey implements Serializable{

    private LocalDateTime start;
    private LocalDateTime stop;
    private String title;
}
