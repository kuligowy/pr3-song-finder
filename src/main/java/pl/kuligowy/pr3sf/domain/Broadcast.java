package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Broadcast {

    @JsonProperty("Title")
    private  String title;
    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    private  LocalDateTime start;
    @JsonProperty("Stop")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    private  LocalDateTime stop;
    @JsonProperty("Songs")
    private  List<SongEntry> songEntries;

    public Broadcast(){

    }
}
