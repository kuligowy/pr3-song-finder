package pl.kuligowy.pr3sf.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeSerializer;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
public class BroadcastDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("songsSize")
    private int songEntrySize;
    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("start")
    private LocalDateTime start;
    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("stop")
    private LocalDateTime stop;

    public BroadcastDTO(Long id, String title, int songEntrySize,LocalDateTime start, LocalDateTime stop) {
        this.id = id;
        this.title = title;
        this.songEntrySize = songEntrySize;
        this.start = start;
        this.stop = stop;
    }
}
