package pl.kuligowy.pr3sf.controllers;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

/**
 * Created by coolig on 27.06.17.
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    @RequestMapping("/search")
    public void search(@RequestParam LocalDate date, @RequestParam Optional<String> value){

    }

}
