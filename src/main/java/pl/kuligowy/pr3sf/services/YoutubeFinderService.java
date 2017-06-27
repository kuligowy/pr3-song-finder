package pl.kuligowy.pr3sf.services;

import org.springframework.stereotype.Service;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.utils.LaunderThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class YoutubeFinderService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private final int MAX_THREADS = 10;
    private final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    private final CompletionService<SongEntry> completionService = new ExecutorCompletionService<>(executor);

    public void find(List<SongEntry> songEntryList){
        songEntryList.forEach(se->completionService.submit(createTask(se)));
        try {
            for (int t = 0, n =  songEntryList.size(); t < n;  t++) {
                Future<SongEntry> f = completionService.take();
                SongEntry songEntry = f.get();
                System.out.println(String.format("%s %s -> %s",songEntry.getArtist(),songEntry.getTitle(),songEntry.getLinks()));
                //TODO: send msg to rabbit
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e);
        }
    }

    private Callable<SongEntry> createTask(SongEntry se){
        return () -> {
            logger.info("Calling...");
            //TODO ask youtube api for results
            Thread.sleep(1500);
            List<String> list = IntStream.range(1,3).mapToObj((i)->"Link "+i).collect(Collectors.toList());
            logger.info("... end.");
            return new SongEntry(se.getArtist(),se.getTitle(),list);
        };
    }

}
