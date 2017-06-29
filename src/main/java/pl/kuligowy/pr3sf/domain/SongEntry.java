package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import lombok.*;
import pl.kuligowy.pr3sf.utils.*;

import javax.persistence.*;
import java.io.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name="song_entry")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SongEntry implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @JsonProperty("Title")
    @Column(name="title")
    private String title;
    @JsonProperty("AlbumTitle")
    @Column(name="album_title")
    private String albumTitle;
    @JsonProperty("Duration")
    @Column(name="duration")
    private Long duration;
    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @JsonSerialize(using=CustomJsonLocalDateTimeSerializer.class)
    @Column(name="start")
    private LocalDateTime start;
    @JsonProperty("Artist")
    @Column(name="artist")
    private String artist;
    @JoinColumn(name = "broadcast",referencedColumnName = "id")
    @ManyToOne
    @JsonBackReference
    private Broadcast broadcast;
    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)//,mappedBy = "songEntryId")
    @JoinColumn(name = "song_entry_id",referencedColumnName = "id")
    private List<YoutubeResult> links;

    public SongEntry() {

    }

    public SongEntry(String artist, String title, List<YoutubeResult> links) {
        this.title = title;
        this.artist = artist;
        this.links = links;
        //this.links.stream().forEach(yt -> yt.setSongEntryId(this));
    }

    public SongEntry(String artist, String title){
        this.title = title;
        this.artist = artist;
    }

    public Comparator getComparator() {
         return Comparator.comparing(SongEntry::getTitle)
                 .thenComparing(SongEntry::getArtist);
                 //.thenComparing(SongEntry::getStart);
     }


}
