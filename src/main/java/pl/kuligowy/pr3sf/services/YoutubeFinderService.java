package pl.kuligowy.pr3sf.services;

import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.respositories.SongEntryRepository;
import pl.kuligowy.pr3sf.utils.LaunderThrowable;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Service
public class YoutubeFinderService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${queue.name}")
    private String queueName;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private YoutubeClientService youtubeService;
    @Autowired
    private SongEntryRepository songEntryRepository;

    public String search(List<SongEntry> songEntryList){
        for(SongEntry se : songEntryList){
//            try {
                youtubeService.searchVideos(se).thenAccept((s)->{
                    String msg = String.format("%d=%s %s -> %s",s.getId(),s.getArtist(),s.getTitle(),s.getLinks());
                    logger.info("Entry: "+msg);
                        logger.info("TITLE WITH DUPA: "+s.getTitle());
                        songEntryRepository.save(s);
                        rabbitTemplate.convertAndSend(queueName, s);
    //                }catch(AmqpConnectException ex){
    //                    logger.info("Rabbit is down, cant send information. Skipping sending info for "+msg);
    //                }
                });
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
        }
        return "search started";
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
//        return "search started";
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
