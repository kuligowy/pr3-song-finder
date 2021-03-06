package pl.kuligowy.pr3sf.services;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.respositories.*;

import java.time.*;
import java.util.*;

@Service
public class BroadcastService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private BroadcastRepository broadcastRepository;
    private PR3Service service;
    private String URL;
    private final RestTemplate rest = new RestTemplate();

    @Autowired
    public BroadcastService(
            PR3Service service,
            BroadcastRepository broadcastRepository,
            @Value("${pr3.rest.api.url}") String URL) {
        this.broadcastRepository = broadcastRepository;
        this.service = service;
        this.URL = URL;
    }

    public List<Broadcast> getAllSongs(Optional<LocalDate> date,Long broadcastId, Pageable page){
        logger.info("Using DATABASE");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list=
                broadcastRepository.findAll(
                BroadcastSpec.getForDayAndBroadcast(day,broadcastId),
                page,
                EntityGraph.EntityGraphType.FETCH,"Broadcast.songs").getContent();
        //
        //
        return list;
    }

    public List<Broadcast> getSongs(Optional<LocalDate> date,long broadcastId){
        logger.info("Using DATABASE");
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list=
                broadcastRepository.findAll(BroadcastSpec.getForDayAndBroadcast(day,broadcastId));
        return list;
    }

    public List<Broadcast> getBroadcastList(Optional<LocalDate> date,Sort sort){
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        List<Broadcast> list=
                broadcastRepository.findAll(BroadcastSpec.getForDay(day),sort);
        service.updateDatabaseFromSource(date);
        return list;
    }


}
