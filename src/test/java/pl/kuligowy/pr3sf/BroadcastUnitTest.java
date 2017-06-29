package pl.kuligowy.pr3sf;


import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.services.BroadcastService;

import java.time.LocalDateTime;
import java.util.List;

public class BroadcastUnitTest {

    @Test
    public void testMergeList(){
//        Broadcast b1 = Broadcast.builder().title("repo 1").start(LocalDateTime.now().minusDays(1)).stop(LocalDateTime.now().minusHours(1)).build();
//        Broadcast b2 = Broadcast.builder().title("repo 2").start(LocalDateTime.now().minusDays(2)).stop(LocalDateTime.now().minusHours(4)).build();
//        SongEntry s1 = SongEntry.builder().title("repo album 1").artist("repo artist 1").build();
//        SongEntry s2 = SongEntry.builder().title("repo album 2").artist("repo artist 2").build();
//        SongEntry s3 = SongEntry.builder().title("repo album 3").artist("repo artist 3").build();
//        SongEntry s4 = SongEntry.builder().title("repo album 4").artist("repo artist 4").build();
//        SongEntry s5 = SongEntry.builder().title("repo album 5").artist("repo artist 5").build();
//        b1.setSongEntries(Lists.newArrayList(s1,s2,s3));
//        b2.setSongEntries(Lists.newArrayList(s4,s5));
//        List<Broadcast> repos = Lists.newArrayList(b1,b2);
//
//        Broadcast bf1 = Broadcast.builder().title("fresh 1").start(LocalDateTime.now().minusDays(1)).stop(LocalDateTime.now().minusHours(1)).build();
//        Broadcast bf2 = Broadcast.builder().title("repo 2").start(b2.getStart()).stop(b2.getStop()).build();
//        SongEntry sf1 = SongEntry.builder().title("fresh album 1").artist("fresh artist 1").build();
//        SongEntry sf2 = SongEntry.builder().title("fresh album 2").artist("fresh artist 1").build();
//        SongEntry sf3 = SongEntry.builder().title("fresh album 3").artist("fresh artist 3").build();
//        SongEntry sf4 = SongEntry.builder().title("fresh album 4").artist("fresh artist 4").build();
//        SongEntry sf5 = SongEntry.builder().title("fresh album 5").artist("fresh artist 5").build();
//        bf1.setSongEntries(Lists.newArrayList(sf1,sf2,sf3));
//        bf2.setSongEntries(Lists.newArrayList(sf4,sf5));
//        List<Broadcast> fresh = Lists.newArrayList(bf1,bf2);
//
//        BroadcastService service = new BroadcastService(null,null);
//        List<Broadcast> ret = service.mergeList(fresh,repos);
//
//        Assert.assertNotEquals(repos.size(),ret.size());
//        Assert.assertNotEquals(repos.get(0).getSongEntries().size(),ret.get(0).getSongEntries().size());
    }

}
