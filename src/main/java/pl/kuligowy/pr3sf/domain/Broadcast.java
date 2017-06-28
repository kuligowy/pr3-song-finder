package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.*;
import java.util.*;

@Data
@Entity
@Table(name="broadcast")
//uniqueConstraints = {    @UniqueConstraint(columnNames = "dummy_id", name = "dummyIdUnique")})
//@IdClass(BroadcastKey.class)
public class Broadcast implements Serializable{

    @EmbeddedId
    private BroadcastKey id;
//    @GeneratedValue
//    @Column(name="id")
//    private Long dummyId;
//    @JsonProperty("Title")
//    @Column(name="title")
//    @Id
//    private  String title;
//    @Id
//    @JsonProperty("Start")
//    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
//    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
//    @Column(name="start")
//    private  LocalDateTime start;
//    @Id
//    @JsonProperty("Stop")
//    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
//    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
//    @Column(name="stop")
//    private  LocalDateTime stop;
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
}
