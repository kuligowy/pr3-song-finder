package pl.kuligowy.pr3sf.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

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

    public BroadcastDTO(Long id, String title, int songEntrySize) {
        this.id = id;
        this.title = title;
        this.songEntrySize = songEntrySize;
    }
}
