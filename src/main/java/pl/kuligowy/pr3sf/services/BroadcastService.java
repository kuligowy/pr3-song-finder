package pl.kuligowy.pr3sf.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;
import pl.kuligowy.pr3sf.respositories.BroadcastSpec;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BroadcastService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private BroadcastRepository broadcastRepository;
    @Autowired
    BasicBroadcastService service;
    private String URL;
    private final RestTemplate rest = new RestTemplate();

    @Autowired
    public BroadcastService(
            BroadcastRepository broadcastRepository, @Value("${pr3.rest.api.url}") String URL ) {
        this.broadcastRepository = broadcastRepository;
        this.URL = URL;
    }

    public List<Broadcast> getAllSongs(Optional<LocalDate> date){
        logger.info("Using DATABASE");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list= broadcastRepository.findAll(BroadcastSpec.getForDay(day));
        service.updateDatabaseFromSource(date);
        return list;
    }

    public List<Broadcast> getSongs(Optional<LocalDate> date,long broadcastId){
        logger.info("Using DATABASE");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list= broadcastRepository.findAll(BroadcastSpec.getForDayAndBroadcast(day,broadcastId));
        return list;
    }

    public List<Broadcast> getBroadcastList(Optional<LocalDate> date){
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list= broadcastRepository.findAll(BroadcastSpec.getForDay(day));
        return list;
    }


}
