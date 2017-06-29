package pl.kuligowy.pr3sf.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pl.kuligowy.pr3sf.domain.*;
import pl.kuligowy.pr3sf.services.*;

import java.time.*;
import java.util.*;
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


    @GetMapping("/broadcast")
    public ResponseEntity<?> getBroadcasts(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<BroadcastDTO> list = broadcastService.getBroadcastList(Optional.ofNullable(day))
               .stream().map(b-> new BroadcastDTO(b.getId(),b.getTitle(),b.getSongEntries().size())).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/songs")
    public ResponseEntity<?> getList(@RequestParam(value = "day",required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        ProgressMarker progress = new ProgressMarker();
        progress.setMessage("Fetching collection of songs");
        progress.setStart(LocalDateTime.now());
        progress.setState(StateMarker.STARTED);
        List<Broadcast> list = broadcastService.getAllSongs(Optional.ofNullable(day));
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/broadcast/{broadcastId}/songs")
    public ResponseEntity<?> getList(@PathVariable long broadcastId,
                                     @RequestParam(value = "day",required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<Broadcast> list = broadcastService.getSongs(Optional.ofNullable(day),broadcastId);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }



    @GetMapping("/show")
    public ResponseEntity<?> showListForDay(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<Broadcast> list = broadcastService.getAllSongs(Optional.ofNullable(day));
        List<SongEntry> songs = list.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(se -> se.stream()).collect(Collectors.toList());
        songs.stream().parallel().forEach(rabbitService::sendMessage);
        ResponseEntity responseEntity = new ResponseEntity(StateMarker.STARTED, HttpStatus.OK);
        return responseEntity;
    }
 
}
