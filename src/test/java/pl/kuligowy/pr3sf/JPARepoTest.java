package pl.kuligowy.pr3sf;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mtkl on 2017-07-03.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
public class JPARepoTest {

    @Autowired
    BroadcastRepository repository;
    @Autowired
    TestEntityManager em;

    @Test
    public void testSorting(){
        Broadcast broadcast1 = new Broadcast();
        broadcast1.setId(10L);
        broadcast1.setStart(LocalDateTime.of(2017,07,03,13,30));
        broadcast1.setStop(LocalDateTime.now());
        broadcast1.setTitle("Test");
        Broadcast broadcast2 = new Broadcast();
        broadcast2.setId(20L);
        broadcast2.setStart(LocalDateTime.of(2017,07,03,15,30));
        broadcast2.setStop(LocalDateTime.now());
        broadcast2.setTitle("Test");
        Broadcast broadcast3 = new Broadcast();
        broadcast3.setId(30L);
        broadcast3.setStart(LocalDateTime.of(2017,07,03,11,30));
        broadcast3.setStop(LocalDateTime.now());
        broadcast3.setTitle("Test");
        List<Broadcast> xx = Lists.newArrayList(broadcast1,broadcast2,broadcast3);

        for(Broadcast b : xx)
            em.persist(b);

        Sort sort = new Sort(Sort.Direction.DESC,"start");
        repository.findAll(sort);
    }
}
