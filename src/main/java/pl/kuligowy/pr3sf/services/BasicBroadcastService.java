package pl.kuligowy.pr3sf.services;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.respositories.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * Created by coolig on 28.06.17.
 */
@Component
public class BasicBroadcastService {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final RestTemplate rest = new RestTemplate();
    @Autowired
    private BroadcastRepository broadcastRepository;
    @Value("${pr3.rest.api.url}")
    private String URL;
    @Autowired
    private YoutubeClientService service;

    @Scheduled(fixedDelay=5000)
    public void downloadBroadcastCollection(){
        LocalDate day  = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dayString = day.format(dtf);
        logger.info("Downloading songs...");
        ResponseEntity<Broadcast[]> response = rest.getForEntity(URL, Broadcast[].class,dayString);
        List<Broadcast> result = Arrays.asList(response.getBody());
        broadcastRepository.save(result);

        List<SongEntry> songs = result.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(list->list.stream())
                .collect(Collectors.toList());

        List<CompletableFuture<SongEntry>> songsWithVideos = songs.stream().map(song -> service.searchVideos(song)).collect(Collectors.toList());
        CompletableFuture.allOf(songsWithVideos.toArray(new CompletableFuture[songsWithVideos.size()]))
                .thenAccept(aVoid -> logger.info("downloaded songs with videos"));
    }

}
