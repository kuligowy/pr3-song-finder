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
public class BroadcastController {

//    @Autowired
//    YoutubeFinderService service;
    BroadcastService broadcastService;
    SongEntryService songService;
//    @Autowired
//    RabbitService rabbitService;
//    @Autowired
//    BasicBroadcastService basicBroadcastService;
    @Autowired
    public BroadcastController(BroadcastService broadcastService,SongEntryService songEntryService){
        this.broadcastService = broadcastService;
        this.songService = songEntryService;
    }

    @GetMapping
    public String simpleMethod(){
        return "test";
    }

    @GetMapping("/broadcast")
    public ResponseEntity<?> getBroadcasts(@RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,Sort sort){
        List<BroadcastDTO> list = broadcastService.getBroadcastList(Optional.ofNullable(day),sort)
//        List<Broadcast> list = broadcastService.getBroadcastList(Optional.ofNullable(day));
               .stream().map(b-> new BroadcastDTO(b.getId(),b.getTitle(),b.getSongEntries().size(),b.getStart(),b.getStop()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/broadcast/{broadcastId}")
    public ResponseEntity<?> getList(    Pageable page,
        @RequestParam(value = "day",required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
    @PathVariable Long broadcastId){
        ProgressMarker progress = ProgressMarker.STARTED();
//        List<Broadcast> list = broadcastService.getAllSongs();
        Page<SongEntry> ret = songService.getSongs(broadcastId,Optional.ofNullable(day),page);
        //        if(list.isEmpty())
//            return new ResponseEntity<>(progress,HttpStatus.ACCEPTED);
        return new ResponseEntity<>(ret,HttpStatus.OK);
    }


}
