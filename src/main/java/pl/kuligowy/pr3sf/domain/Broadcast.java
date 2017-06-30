package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import lombok.*;
import org.hibernate.annotations.*;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeSerializer;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="broadcast")
@ToString(exclude = "songEntries")
@EqualsAndHashCode(exclude = "songEntries")
@NamedEntityGraph(name = "Broadcast.songs",
        attributeNodes = @NamedAttributeNode("songEntries"))
public class Broadcast implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="start")
    private LocalDateTime start;
    @Column(name="stop")
    private LocalDateTime stop;
//    @JsonProperty("Title")
    @Column(name="title")
    private String title;
//    @JsonProperty("Songs")
    @OneToMany(mappedBy = "broadcast",cascade = CascadeType.PERSIST)
    private  List<SongEntry> songEntries;
    @Column(name = "last_update")
    @UpdateTimestamp
    private Date lastUpdated;
    @CreationTimestamp
    private Date inserted;

    public Broadcast(){
        this.songEntries = Lists.newArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
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

    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("stop")
    public LocalDateTime getStop() {
        return stop;
    }

    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @JsonProperty("Stop")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }

    @JsonProperty(value = "title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("songs")
    @JsonManagedReference
    public List<SongEntry> getSongEntries() {
        return songEntries;
    }

    @JsonProperty("Songs")
    @JsonManagedReference
    public void setSongEntries(List<SongEntry> songEntries) {
        this.songEntries = songEntries;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    public Comparator getComparator() {
        return Comparator.comparing(Broadcast::getTitle)
                .thenComparing(Broadcast::getStop)
                .thenComparing(Broadcast::getStart);
    }
}
