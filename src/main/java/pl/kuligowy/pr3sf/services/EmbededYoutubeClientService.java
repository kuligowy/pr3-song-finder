package pl.kuligowy.pr3sf.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.domain.YoutubeResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Profile("dev")
public class EmbededYoutubeClientService implements YoutubeService {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final YouTube youtube;
    @Value("${google.apikey}")
    private String API_KEY;
    @Value("${google.youtube.maxVids}")
    private Long NUMBER_OF_VIDEOS_RETURNED;

    public EmbededYoutubeClientService() throws GeneralSecurityException, IOException {
        logger.info("Creating EmbededYoutubeClientService");
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();
        youtube = new YouTube.Builder(transport, jacksonFactory, request -> {})
                .setApplicationName("pr3-song-finder").build();
    }

    @Async("youtubeExecutor")
    public CompletableFuture<SongEntry> searchVideos(SongEntry songEntry){
        logger.info(String.format("Searching for: %s - %s",songEntry.getArtist(),songEntry.getTitle()));
        try {
            List<YoutubeResult> linksList =  IntStream.range(0,3).mapToObj(i -> {
                YoutubeResult yt = new YoutubeResult("Rajof9Qigos"+songEntry.getId()+i);
                return yt;
            }).collect(Collectors.toList());
            songEntry.setLinks(linksList);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return CompletableFuture.completedFuture(songEntry);
    }

}
