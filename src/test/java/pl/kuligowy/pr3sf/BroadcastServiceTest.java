package pl.kuligowy.pr3sf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;
import pl.kuligowy.pr3sf.services.BroadcastService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BroadcastServiceTest {

    @Value("${pr3.rest.api.url}")
    String URL;

    RestTemplate rest = new RestTemplate();

    @Test
    public void testUnwrappingJson(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Broadcast[]> response = rest.exchange(URL, HttpMethod.GET,request,Broadcast[].class,"2017-06-28");
        Assert.assertNotNull(response.getBody()[0].getSongEntries());
        System.out.println(response.getBody()[0].getSongEntries().get(0).getTitle());
        Assert.assertNotNull(response.getBody()[0].getSongEntries().get(0).getTitle());
        Assert.assertNotNull(response.getBody()[0].getSongEntries().get(0).getArtist());

    }

}
