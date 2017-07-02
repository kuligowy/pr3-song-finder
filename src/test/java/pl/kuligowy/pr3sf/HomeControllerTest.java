package pl.kuligowy.pr3sf;

import org.assertj.core.util.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import pl.kuligowy.pr3sf.controllers.*;
import pl.kuligowy.pr3sf.services.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(HomeController.class)
//@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BroadcastService service;
//    @Mock
//    BasicBroadcastService basicBroadcastService;
//    @Mock
//    BroadcastRepository broadcastRepository;
    @MockBean
EntityManagerFactory entityManagerFactory;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
//        mvc = MockMvcBuilders.standaloneSetup(service).build();

    }

    @Test
    public void testSearchNoDate() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        Mockito.when(service.getAllSongs(Optional.empty(),broadcastId,page))
                .thenReturn(Lists.newArrayList());
//        BDDMockito.given(service.getAllSongs(Optional.empty(),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get("/api/broadcast/{broadcastId}/song",broadcastId)).andExpect(status().isAccepted());
    }

    @Test
    public void testSimpleMethod() throws Exception {
        mvc.perform(get("/api")).andExpect(status().isOk());
    }

    @Test
    public void testSearchToday() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now()),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get("/api/downloadListForDay?day={day}", LocalDate.now())).andExpect(status().isOk());
    }

    @Test
    public void testSearchYesterday() throws Exception {
        Pageable page = new PageRequest(0,10);
        Long broadcastId = 1L;
        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now().minusDays(1)),broadcastId,page)).willReturn(Lists.newArrayList());
        mvc.perform(get("/api/downloadListForDay?day={day}", LocalDate.now().minusDays(1)))
                .andExpect(status().isOk());
    }
}
