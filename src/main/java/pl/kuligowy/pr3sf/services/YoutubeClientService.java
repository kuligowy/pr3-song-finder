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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.domain.YoutubeResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class YoutubeClientService {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final YouTube youtube;
    @Value("${google.apikey}")
    private String API_KEY;
    @Value("${google.youtube.maxVids}")
    private Long NUMBER_OF_VIDEOS_RETURNED;

    public YoutubeClientService() throws GeneralSecurityException, IOException {
        logger.info("Creating YoutubeClientService");
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JacksonFactory jacksonFactory = new com.google.api.client.json.jackson2.JacksonFactory();
        youtube = new YouTube.Builder(transport, jacksonFactory, request -> {})
                .setApplicationName("pr3-song-finder").build();
    }

    @Async
    public CompletableFuture<SongEntry> searchVideos(SongEntry songEntry){
        logger.info(String.format("Searching for: %s - %s",songEntry.getArtist(),songEntry.getTitle()));
        try {
            String queryTerm = songEntry.getArtist()+" "+songEntry.getTitle();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(API_KEY);
            search.setQ(queryTerm);
            search.setType("video");
            search.setFields("items(id,id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<YoutubeResult> links = createLinks(searchResultList.iterator());
            songEntry.setLinks(links);
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return CompletableFuture.completedFuture(songEntry);
    }

    private List<YoutubeResult> createLinks(Iterator<SearchResult> iteratorSearchResults){
        List<YoutubeResult> links = Lists.newArrayList();
        while (iteratorSearchResults.hasNext()) {
            SearchResult singleVideo = iteratorSearchResults.next();
            String id = singleVideo.getId().getVideoId();
            YoutubeResult result = new YoutubeResult(id);
            links.add(result);
        }
        return links;
        //return new SongEntry(se.getArtist(),se.getTitle(),links);
    }

    private void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

                System.out.println(" Video Id " + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }
}
