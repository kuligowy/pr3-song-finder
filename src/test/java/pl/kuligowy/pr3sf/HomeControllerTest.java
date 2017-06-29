//package pl.kuligowy.pr3sf;
//
//import org.assertj.core.util.Lists;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import pl.kuligowy.pr3sf.controllers.HomeController;
//import pl.kuligowy.pr3sf.services.BroadcastService;
//import pl.kuligowy.pr3sf.services.YoutubeFinderService;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {HomeController.class})
//public class HomeControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private BroadcastService service;
//    @MockBean
//    YoutubeFinderService ytservice;
//
//    @Before
//    public void setup(){
//
//    }
//
//    @Test
//    public void testSearchNoDate() throws Exception {
//        BDDMockito.given(service.getAllSongs(Optional.empty())).willReturn(Lists.newArrayList());
//        mvc.perform(get("/api/downloadListForDay")).andExpect(status().isOk());
//    }
//
//    @Test
//    public void testSearchToday() throws Exception {
//
//        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now()))).willReturn(Lists.newArrayList());
//        mvc.perform(get("/api/downloadListForDay?day={day}", LocalDate.now())).andExpect(status().isOk());
//    }
//
//    @Test
//    public void testSearchYesterday() throws Exception {
//        BDDMockito.given(service.getAllSongs(Optional.of(LocalDate.now().minusDays(1)))).willReturn(Lists.newArrayList());
//        mvc.perform(get("/api/downloadListForDay?day={day}", LocalDate.now().minusDays(1)))
//                .andExpect(status().isOk());
//    }
//}
