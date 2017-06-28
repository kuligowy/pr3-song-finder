package pl.kuligowy.pr3sf.services;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BroadcastService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private BroadcastRepository broadcastRepository;
    private String URL;
    private final RestTemplate rest = new RestTemplate();

    @Autowired
    public BroadcastService(
            BroadcastRepository broadcastRepository, @Value("${pr3.rest.api.url}") String URL ) {
        this.broadcastRepository = broadcastRepository;
        this.URL = URL;
    }

    public List<Broadcast> getFromRepository(Optional<LocalDate> date){
        logger.info("Using DATABASE");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        LocalDateTime dateX = LocalDateTime.of(day, LocalTime.MIN);
        LocalDateTime dateY = LocalDateTime.of(day, LocalTime.MAX);
        return broadcastRepository.findAll((root, query, cb) -> cb.between(root.get("start"),dateX,dateY));
    }

    public List<Broadcast> getFromSource(Optional<LocalDate> date){
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dayString = day.format(dtf);
        logger.info("Using REST API");
        ResponseEntity<Broadcast[]> response = rest.getForEntity(URL, Broadcast[].class,dayString);
        List<Broadcast> result = Arrays.asList(response.getBody());
        return broadcastRepository.save(result);
    }

}
