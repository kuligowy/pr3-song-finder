package pl.kuligowy.pr3sf.domain;

import lombok.Data;

import java.util.List;

@Data
public class SongEntry {

    private final String title;
    private final String artist;
    private List<String> links;

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
