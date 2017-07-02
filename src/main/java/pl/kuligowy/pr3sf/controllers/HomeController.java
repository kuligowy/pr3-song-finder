package pl.kuligowy.pr3sf.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
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

//    @Autowired
//    YoutubeFinderService service;
    BroadcastService broadcastService;
//    @Autowired
//    RabbitService rabbitService;
//    @Autowired
//    BasicBroadcastService basicBroadcastService;
    @Autowired
    public HomeController(BroadcastService broadcastService){
        this.broadcastService = broadcastService;
    }

    @GetMapping
    public String simpleMethod(){
        return "test";
    }

    @GetMapping("/broadcast")
    public ResponseEntity<?> getBroadcasts(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<BroadcastDTO> list = broadcastService.getBroadcastList(Optional.ofNullable(day))
               .stream().map(b-> new BroadcastDTO(b.getId(),b.getTitle(),b.getSongEntries().size())).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/broadcast/{broadcastId}/song")
    public ResponseEntity<?> getList(
    @RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
    @PathVariable Long broadcastId,
    Pageable page){
        ProgressMarker progress = new ProgressMarker();
        progress.setMessage("Fetching collection of songs, refresh in a minute...");
        progress.setStart(LocalDateTime.now());
        progress.setState(StateMarker.STARTED);
        List<Broadcast> list = broadcastService.getAllSongs(Optional.ofNullable(day),broadcastId,page);
        if(list.isEmpty())
            return new ResponseEntity<>(progress,HttpStatus.ACCEPTED);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

//    @GetMapping("/broadcast/{broadcastId}/songs")
//    public ResponseEntity<?> getList(@PathVariable long broadcastId,
//                                     @RequestParam(value = "day",required = false)
//                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
//        List<Broadcast> list = broadcastService.getSongs(Optional.ofNullable(day),broadcastId);
//        return new ResponseEntity<>(list,HttpStatus.OK);
//    }

//    @GetMapping("/show")
//    public ResponseEntity<?> showListForDay(@RequestParam(value = "day",required = false)
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
//        List<Broadcast> list = broadcastService.getAllSongs(Optional.ofNullable(day));
//        List<SongEntry> songs = list.stream().map(broadcast -> broadcast.getSongEntries()).flatMap(se -> se.stream()).collect(Collectors.toList());
//        songs.stream().parallel().forEach(rabbitService::sendMessage);
//        ResponseEntity responseEntity = new ResponseEntity(StateMarker.STARTED, HttpStatus.OK);
//        return responseEntity;
//    }

}
