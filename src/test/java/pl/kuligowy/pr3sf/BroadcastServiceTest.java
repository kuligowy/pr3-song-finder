package pl.kuligowy.pr3sf;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;
import pl.kuligowy.pr3sf.services.PR3Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BroadcastServiceTest {

    @Autowired
    PR3Service service;
    @MockBean
    BroadcastRepository repository;

    @Test
    public void testPR3WithDate(){
        LocalDate day = LocalDate.now();

        LocalDateTime dateX = LocalDateTime.of(day, LocalTime.MIN);
        LocalDateTime dateY = LocalDateTime.of(day, LocalTime.MAX);
        given(repository.count((root, query, cb) -> cb.between(root.get("start"),dateX,dateY))).willReturn(0L);

        List<Broadcast> list = service.getSongs(Optional.of(LocalDate.now()));

        Assert.assertEquals(list.size(),0);
    }

}
