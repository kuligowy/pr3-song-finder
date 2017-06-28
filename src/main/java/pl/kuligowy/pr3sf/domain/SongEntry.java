package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="song_entry")
public class SongEntry implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="Title")
    @JsonProperty("Title")
    private String title;
    @JsonProperty("AlbumTitle")
    @Column(name="AlbumTitle")
    private String albumTitle;
    @JsonProperty("Duration")
    @Column(name="Duration")
    private Long duration;
    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @Column(name="Start")
    private LocalDateTime start;
    @JsonProperty("Artist")
    @Column(name="Artist")
    private String artist;
    @JoinColumn(name = "broadcast",referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Broadcast broadcast;
    @Transient
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
