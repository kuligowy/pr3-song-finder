package pl.kuligowy.pr3sf.services;

import pl.kuligowy.pr3sf.domain.SongEntry;

import java.util.concurrent.CompletableFuture;

/**
 * Created by mtkl on 2017-06-30.
 */
public interface YoutubeService {

     CompletableFuture<SongEntry> searchVideos(SongEntry songEntry);
}
