package pl.kuligowy.pr3sf.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import pl.kuligowy.pr3sf.utils.CustomJsonLocalDateTimeDeserializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="broadcast")
public class Broadcast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
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
    @OneToMany(mappedBy = "broadcast",cascade = CascadeType.PERSIST)
    private  List<SongEntry> songEntries;

    public Broadcast(){

    }
}
