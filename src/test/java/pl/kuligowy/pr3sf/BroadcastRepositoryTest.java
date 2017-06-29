package pl.kuligowy.pr3sf;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.respositories.BroadcastRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BroadcastRepositoryTest {


    @Autowired
    private BroadcastRepository repository;

    @Test
    public void saveBroadcast(){
        Broadcast broadcast = new Broadcast();
        List<SongEntry> list = Lists.newArrayList();
        SongEntry se = new SongEntry();
        se.setTitle("Test title");
        se.setAlbumTitle("Album title");
        se.setArtist("Artits");
        se.setBroadcast(broadcast);
        list.add(se);
        broadcast.setSongEntries(list);

        Broadcast saved = repository.save(broadcast);

        Assert.assertNotNull(saved.getSongEntries());
        Assert.assertNotNull(saved.getSongEntries().get(0));
        Assert.assertNotNull(saved.getSongEntries().get(0).getArtist());
        Assert.assertNotNull(saved.getSongEntries().get(0).getTitle());
    }

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
