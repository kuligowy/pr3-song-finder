package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.*;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="broadcast")
//uniqueConstraints = {    @UniqueConstraint(columnNames = "dummy_id", name = "dummyIdUnique")})
//@IdClass(BroadcastKey.class)
public class Broadcast implements Serializable{

    @GeneratedValue
    @Id
    @Column(name="id")
    private Long dummyId;
    @JsonProperty("Title")
    @Column(name="title")
    private  String title;
    @JsonProperty("Start")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @Column(name="start")
    private  LocalDateTime start;
    @JsonProperty("Stop")
    @JsonFormat(pattern = "E, d MMM yyyy HH:mm:ss")
    @JsonDeserialize(using = CustomJsonLocalDateTimeDeserializer.class)
    @Column(name="stop")
    private  LocalDateTime stop;
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
