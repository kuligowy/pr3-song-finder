package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SongEntry implements Serializable{

    @JsonProperty("Title")
    private String title;
    @JsonProperty("AlbumTitle")
    private String albumTitle;
    @JsonProperty("Duration")
    private Long duration;
    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    private LocalDateTime start;
    @JsonProperty("Artist")
    private String artist;
    private List<String> links;

    public SongEntry() {

    }

    public SongEntry(String artist, String title, List<String> links) {
        this.title = title;
        this.artist = artist;
        this.links = links;
    }

    public SongEntry(String artist, String title){
        this.title = title;
        this.artist = artist;
    }
}
