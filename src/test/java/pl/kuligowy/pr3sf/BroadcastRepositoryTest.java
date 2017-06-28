package pl.kuligowy.pr3sf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BroadcastRepositoryTest {


    @Autowired
    private BroadcastRepository repository;

    @Test
    public void test(){
        LocalDateTime dateX = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dateY = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);
        System.out.println(dateX);
        System.out.println(dateY);
        long count = repository.count((root, query, cb) -> {
            return cb.between(root.get("start"),dateX,dateY);
        });
        Assert.assertEquals(count,0);
    }

}
