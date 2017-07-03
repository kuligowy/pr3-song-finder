package pl.kuligowy.pr3sf;

import org.assertj.core.util.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.config.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import pl.kuligowy.pr3sf.controllers.*;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.services.*;

import java.time.*;
import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BroadcastController.class)
@EnableSpringDataWebSupport
public class BroadcastControllerTest {

    private final String API_URL="/api/broadcast";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BroadcastService service;
    @MockBean
    private SongEntryService songEntryService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchNoDate() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        Mockito.when(service.getAllSongs(Optional.empty(),broadcastId,page))
                .thenReturn(Lists.newArrayList());
//        BDDMockito.given(service.getAllSongs(Optional.empty(),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get(API_URL+"?size=1&page=1",broadcastId)).andExpect(status().isOk());
    }

    @Test
    public void testSimpleMethod() throws Exception {
        Broadcast broadcast = new Broadcast();
        broadcast.setId(10L);
        broadcast.setStart(LocalDateTime.of(2017,07,03,13,30));
        broadcast.setStop(LocalDateTime.now());
        broadcast.setTitle("Test");
        List<Broadcast> xx = Lists.newArrayList(broadcast);
        Mockito.when(service.getBroadcastList(Optional.empty(),null))
                .thenReturn(xx);
        MvcResult result =
                mvc.perform(get(API_URL))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].start").value("2017-07-03 13:30")).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDateSort() throws Exception {
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
        Sort sort = new Sort( Sort.Direction.DESC,"start");
        Mockito.when(service.getBroadcastList(Optional.empty(),sort))
                .thenReturn(xx);
        MvcResult result =
                mvc.perform(get(API_URL))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].start").value("2017-07-03 11:30"))
                        .andExpect(jsonPath("$.[1].start").value("2017-07-03 13:30"))
                        .andExpect(jsonPath("$.[2].start").value("2017-07-03 15:30")).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testSimpleDate() throws Exception {

        mvc.perform(get("/api")).andExpect(status().isOk());
    }

    @Test
    public void testSearchToday() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now()),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get(API_URL+"?size=1&page=1&day={day}", broadcastId,LocalDate.now())).andExpect(status().isOk());
    }

    @Test
    public void testSearchYesterday() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now().minusDays(1)),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get(API_URL+"?size=1&page=1&day={day}",broadcastId, LocalDate.now().minusDays(1)))
                .andExpect(status().isOk());
    }
}
