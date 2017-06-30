package pl.kuligowy.pr3sf.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table
@Entity
public class YoutubeResult implements Serializable{

    @Id
    @Column(name="video_id")
    private String videoId;
    @Column(name="good_counter")
    private int goodCounter;
    @Column(name="bad_counter")
    private int badCounter;
//    @ManyToOne
//    @JoinColumn(name = "song_entry_id",referencedColumnName = "id")
//    private SongEntry songEntryId;
//    @Column(name="song_entry_id")
//    private long songEntryId;

    public YoutubeResult(){

    }


    public YoutubeResult(String id) {
        this.videoId = id;
    }

    public String getLink(){
        return String.format("http://youtube.com/watch?v=%s",videoId);
    }

}
