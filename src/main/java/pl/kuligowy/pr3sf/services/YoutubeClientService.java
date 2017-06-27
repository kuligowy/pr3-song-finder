package pl.kuligowy.pr3sf.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
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
import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

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

    public SongEntry searchVideos(SongEntry songEntry){
        logger.info(String.format("Searching for: %s - %s",songEntry.getArtist(),songEntry.getTitle()));
        try {
            // Prompt the user to enter a query term.
            String queryTerm = songEntry.getArtist()+" "+songEntry.getTitle();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setKey(API_KEY);
            search.setQ(queryTerm);
            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");
            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id,id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
//            if (searchResultList != null) {
//                prettyPrint(searchResultList.iterator(), queryTerm);
//            }
            return createLink(searchResultList.iterator(),songEntry);
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return songEntry;
    }

    private SongEntry createLink(Iterator<SearchResult> iteratorSearchResults,SongEntry se){
        List<String> links = Lists.newArrayList();
        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            String id = singleVideo.getId().getVideoId();
            links.add(String.format("http://youtube.com/watch?v=%s",id));
        }
        return new SongEntry(se.getArtist(),se.getTitle(),links);
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
