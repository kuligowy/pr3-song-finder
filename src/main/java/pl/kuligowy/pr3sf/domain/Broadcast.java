package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Getter
@Setter
@ToString(exclude = "songEntries")
@EqualsAndHashCode(exclude = "songEntries")
public class Broadcast implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("Start")
    @Column(name="start")
    private LocalDateTime start;
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomJsonLocalDateTimeSerializer.class)
    @JsonProperty("Stop")
    @Column(name="stop")
    private LocalDateTime stop;
    @JsonProperty("Title")
    @Column(name="title")
    private String title;
    @JsonProperty("Songs")
    @JsonManagedReference
    @OneToMany(mappedBy = "broadcast",cascade = CascadeType.PERSIST)
    private  List<SongEntry> songEntries;
    @Column(name = "last_update")
    @UpdateTimestamp
    private Date lastUpdated;
    @CreationTimestamp
    private Date inserted;

    public Broadcast(){

    }

    public Comparator getComparator() {
        return Comparator.comparing(Broadcast::getTitle)
                .thenComparing(Broadcast::getStop)
                .thenComparing(Broadcast::getStart);
    }
}
