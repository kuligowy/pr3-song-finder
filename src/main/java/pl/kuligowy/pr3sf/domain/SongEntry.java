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

@ToString
@EqualsAndHashCode
public class SongEntry implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="album_title")
    private String albumTitle;
    @Column(name="duration")
    private Long duration;
    @Column(name="start")
    private LocalDateTime start;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("albumTitle")
    public String getAlbumTitle() {
        return albumTitle;
    }

    @JsonProperty("AlbumTitle")
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    @JsonProperty("duration")
    public Long getDuration() {
        return duration;
    }

    @JsonProperty("Duration")
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @JsonSerialize(using=CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("start")
    public LocalDateTime getStart() {
        return start;
    }

    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @JsonProperty("artist")
    public String getArtist() {
        return artist;
    }

    @JsonProperty("Artist")
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public List<YoutubeResult> getLinks() {
        return links;
    }

    public void setLinks(List<YoutubeResult> links) {
        this.links = links;
    }

    public Comparator getComparator() {
         return Comparator.comparing(SongEntry::getTitle)
                 .thenComparing(SongEntry::getArtist);
                 //.thenComparing(SongEntry::getStart);
     }


}
