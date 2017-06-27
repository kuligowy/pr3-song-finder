package pl.kuligowy.pr3sf.services;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kuligowy.pr3sf.domain.Broadcast;

import javax.xml.ws.Response;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PR3Service {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final RestTemplate rest = new RestTemplate();
    @Value("${pr3.rest.api.url}")
    private String URL;

    public List<Broadcast> getSongs(Optional<LocalDate> date){
        ResponseEntity<Broadcast[]> response = rest.getForEntity(URL, Broadcast[].class,"2017-06-23");
        logger.info(String.format("Body: %s",response.getBody()));
        logger.info(String.format("Body: %s",response.getBody().getClass()));
        return Arrays.asList(response.getBody());

    }
}
