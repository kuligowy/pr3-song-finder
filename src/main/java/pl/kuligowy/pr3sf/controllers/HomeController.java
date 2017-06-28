package pl.kuligowy.pr3sf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.domain.StateMarker;
import pl.kuligowy.pr3sf.services.BroadcastService;
import pl.kuligowy.pr3sf.services.RabbitService;
import pl.kuligowy.pr3sf.services.YoutubeFinderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    YoutubeFinderService service;
    @Autowired
    BroadcastService broadcastService;
    @Autowired
    RabbitService rabbitService;


    @GetMapping("/show")
    public ResponseEntity<?> showListForDay(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<Broadcast> list = broadcastService.getFromRepository(Optional.ofNullable(day));
        List<SongEntry> songs = list.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(se -> se.stream()).collect(Collectors.toList());
        songs.stream().parallel().forEach(rabbitService::sendMessage);
        ResponseEntity responseEntity = new ResponseEntity(StateMarker.STARTED, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadListForDay(@RequestParam(value = "day",required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<Broadcast> list = broadcastService.getFromSource(Optional.ofNullable(day));
        List<SongEntry> songs = list.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(se -> se.stream()).collect(Collectors.toList());
        service.search(songs);
        ResponseEntity responseEntity = new ResponseEntity(StateMarker.STARTED, HttpStatus.OK);
        return responseEntity;
    }
}
