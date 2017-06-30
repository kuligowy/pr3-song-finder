package pl.kuligowy.pr3sf.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.respositories.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by coolig on 28.06.17.
 */
@Component
public class BasicBroadcastService {

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final RestTemplate rest = new RestTemplate();
    private final BroadcastRepository broadcastRepository;
    private final SongEntryRepository songEntryRepository;
    @Value("${pr3.rest.api.url}")
    private String URL;
    private final YoutubeService service;

    @Autowired
    public BasicBroadcastService(BroadcastRepository broadcastRepository, SongEntryRepository songEntryRepository, YoutubeService service) {
        this.broadcastRepository = broadcastRepository;
        this.songEntryRepository = songEntryRepository;
        this.service = service;
    }

    @Async("commonExecutor")
    void updateDatabaseFromSource(Optional<LocalDate> date) {
        logger.info("async updateDatabaseFromSource: Using REST API");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dayString = day.format(dtf);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Broadcast[]> response = rest.exchange(URL, HttpMethod.GET, request, Broadcast[].class, dayString);

        List<Broadcast> fresh = Arrays.asList(response.getBody());
        List<Broadcast> previous = broadcastRepository.findAll(BroadcastSpec.getForDay(day), EntityGraph.EntityGraphType.LOAD,"Broadcast.songs");
        List<Broadcast> merged = mergeList(fresh,previous);
        broadcastRepository.save(merged);
        List<SongEntry> mergedWithLinks = findLinks(merged);
        songEntryRepository.save(mergedWithLinks);
    }

    private List<SongEntry> findLinks(List<Broadcast> merged){
        logger.info("Finding links...");
            List<SongEntry> list = merged.stream()
                    .map(b -> b.getSongEntries())
                    .flatMap(s->s.stream())
                    .filter(se-> (se.getLinks()==null || se.getLinks().isEmpty()))
                    .map(se-> service.searchVideos(se))
                    .map(cf -> cf.join())
                    .collect(Collectors.toList());
        return list;
    }

    private List<Broadcast> mergeList(List<Broadcast> fresh, List<Broadcast> previous){
        logger.info("Merging lists...");
        Set<Broadcast> ret = Sets.newHashSet();
        for(Broadcast newBroadcast : fresh){
            Optional<Broadcast> foundBroadcast = previous.stream().filter(b-> b.getComparator().compare(b,newBroadcast)==0).findFirst();

            if(foundBroadcast.isPresent()){
                for(SongEntry newSong: newBroadcast.getSongEntries()){
                    Optional<SongEntry> foundSong = foundBroadcast.get()
                            .getSongEntries()
                            .stream().peek(s->System.out.println(s.getArtist()+" "+s.getTitle()+" "+s.getStart()))
                            .filter(s-> s.getComparator().compare(s,newSong)==0)
                            .findFirst();
                    if(!foundSong.isPresent()){
                        foundBroadcast.get().getSongEntries().add(newSong);
                        System.out.println("Dodaje Piosenke "+newSong.getArtist()+" "+newSong.getTitle() +" do : "+foundBroadcast.get().getTitle());
                        ret.add(foundBroadcast.get());
                    }
                }
            }else{
                System.out.println("Dodaje caly Broadcast "+newBroadcast.getTitle());
                newBroadcast.getSongEntries().stream().forEach(s->System.out.println("\tSong "+s.getTitle()+" br: "+s.getBroadcast()));
                ret.add(newBroadcast);
            }
        }
        List l =  Lists.newArrayList();
        l.addAll(ret);
        return l;
    }

    //@Scheduled(cron = "0 10 * * * *")
    public void downloadBroadcastCollection(){
        LocalDate day  = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dayString = day.format(dtf);
        logger.info("Downloading songs...");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Broadcast[]> response = rest.exchange(URL, HttpMethod.GET,request,Broadcast[].class,dayString);
        List<Broadcast> result = Arrays.asList(response.getBody());
        broadcastRepository.save(result);

//        List<SongEntry> songs = result.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(list->list.stream())
//                .collect(Collectors.toList());
//
//        List<CompletableFuture<SongEntry>> songsWithVideos = songs.stream().map(song -> service.searchVideos(song)).collect(Collectors.toList());
//        CompletableFuture.allOf(songsWithVideos.toArray(new CompletableFuture[songsWithVideos.size()]))
//                .thenAccept(aVoid -> logger.info("downloaded songs with videos"));
    }

}
