package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="song_entry")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
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
    @JsonBackReference
    private Broadcast broadcast;
    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)//,mappedBy = "songEntryId")
    @JoinColumn(name = "song_entry_id", referencedColumnName= "id")
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

    @Override
    public String toString(){
        return "SongEntry"+id;
    }
}
