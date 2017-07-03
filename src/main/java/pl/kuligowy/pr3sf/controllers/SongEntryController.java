package pl.kuligowy.pr3sf.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.*;
import org.springframework.web.bind.annotation.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.services.*;

import java.time.*;
import java.util.*;

/**
 * Created by coolig on 02.07.17.
 */
@RestController
@RequestMapping("/api/song")
public class SongEntryController {

    @Autowired
    SongEntryService service;

    @GetMapping("/{broadcastId}")
    public Page<SongEntry> getAllSongs(   Pageable page,
                                          @RequestParam(value = "day",required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                          @PathVariable Long broadcastId){
        return service.getSongs(broadcastId,Optional.ofNullable(day),page);
    }

    @GetMapping()
    public Page<SongEntry> getAllSongs(   Pageable page,
                                          @RequestParam(value = "day",required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        return service.getSongs(Optional.ofNullable(day),page);
    }
}
