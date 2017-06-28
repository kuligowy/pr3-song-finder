package pl.kuligowy.pr3sf.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.services.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    YoutubeFinderService service;
    @Autowired
    BroadcastService broadcastService;
    @Autowired
    RabbitService rabbitService;
    @Autowired
    BasicBroadcastService basicBroadcastService;



    public ResponseEntity<?> getList(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        ProgressMarker progress = new ProgressMarker();
        progress.setMessage("Fetching collection of songs");
        progress.setStart(LocalDateTime.now());
        progress.setState(StateMarker.STARTED);
        return new ResponseEntity<>(progress,HttpStatus.ACCEPTED);
    }


    @GetMapping("/async")
    public Future<List<Broadcast>> asyncMethod(@RequestParam(value = "day",required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        return CompletableFuture.supplyAsync(() -> {
            List<Broadcast> list =
                broadcastService.getFromRepository(Optional.ofNullable(day));
            if(list.isEmpty()){
                return broadcastService.getFromSource(Optional.ofNullable(day));
            }
            return list;
        });
    }

    @GetMapping("/broadcast")
    public List<Broadcast> noasyncMethod(@RequestParam(value = "day",required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<Broadcast> list =  broadcastService.getFromRepository(Optional.ofNullable(day));
        return list;
    }

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
