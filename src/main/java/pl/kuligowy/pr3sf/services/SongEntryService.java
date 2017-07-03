package pl.kuligowy.pr3sf.services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.respositories.*;

import java.time.*;
import java.util.*;

/**
 * Created by coolig on 02.07.17.
 */
@Service
public class SongEntryService {

    @Autowired
    SongEntryRepository repository;

//    public List<SongEntry> getSongs(Long broadcastId, Optional<LocalDate> date, Pageable page){
//        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
//        return repository.findAll(SongEntrySpec.getForDayAndBroadcast(day,broadcastId),page).getContent();
//    }
    public Page<SongEntry> getSongs(Long broadcastId, Optional<LocalDate> date, Pageable page){
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        return repository.findAll(SongEntrySpec.getForDayAndBroadcast(day,broadcastId),page);
    }
    public Page<SongEntry> getSongs(Optional<LocalDate> date, Pageable page){
        LocalDate day = date.isPresent() ? date.get() : LocalDate.now();
        return repository.findAll(SongEntrySpec.getForDay(day),page);

    }

}
