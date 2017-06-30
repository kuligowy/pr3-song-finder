package pl.kuligowy.pr3sf.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.respositories.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

@Service
public class YoutubeFinderService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private YoutubeService youtubeService;
    private SongEntryRepository songEntryRepository;
    private RabbitService rabbitService;

    @Autowired
    YoutubeFinderService(YoutubeService youtubeClientService,SongEntryRepository repository,RabbitService rabbit){
        this.youtubeService = youtubeClientService;
        this.songEntryRepository = repository;
        this.rabbitService = rabbit;
    }

    public String search(SongEntry songEntry){
        CompletableFuture<SongEntry> ret = youtubeService.searchVideos(songEntry);
        ret.thenAccept(se->{
            String msg = String.format("%d=%s %s -> %s",se.getId(),se.getArtist(),
                    se.getTitle(),se.getLinks());
            songEntryRepository.save(se);
            rabbitService.sendMessage(se);
        }).exceptionally(t->{
            logger.warning("EXCEPTION "+t.getMessage());
            return null;
        });
        return "started";
    }

    public String search(List<SongEntry> songEntryList){
        songEntryList.parallelStream().forEach(this::search);
        return "started";
    }


//    private final int MAX_THREADS = 10;
//    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
//    private final CompletionService<SongEntry> completionService = new ExecutorCompletionService<>(executor);


//    public String searchConcurrently(List<SongEntry> songEntryList){
//        songEntryList.forEach(se->completionService.submit(createSearchTask(se)));
//        try {
//            for (int t = 0, n =  songEntryList.size(); t < n;  t++) {
//                Future<SongEntry> f = completionService.take();
//                SongEntry songEntry = f.get();
//                String msg = String.format("%s %s -> %s",songEntry.getArtist(),songEntry.getTitle(),songEntry.getLinks());
//                System.out.println(msg);
//                try {
//                    rabbitTemplate.convertAndSend(queueName, songEntry);
//                }catch(AmqpConnectException ex){
//                    logger.info("Rabbit is down, cant send information. Skipping sending info");
//                }
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        } catch (ExecutionException e) {
//            throw LaunderThrowable.launderThrowable(e);
//        }
//        return "downloadListForDay started";
//    }







//    private Callable<SongEntry> createSearchTask(SongEntry se){
//        return () -> {
//            logger.info("Searching for videos..."+se.getTitle());
//            Thread.sleep(500);
//            return se;
//            //return youtubeService.searchVideos(se);
//        };
//    }

}
