package pl.kuligowy.pr3sf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.services.PR3Service;
import pl.kuligowy.pr3sf.services.YoutubeFinderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan
@EntityScan(
		basePackageClasses = { Pr3SfApplication.class, Jsr310JpaConverters.class }
)
@EnableAsync
public class Pr3SfApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pr3SfApplication.class, args);
	}


	public void enjoy(){

//		String[] titles = {
//		"Closer (feat. Halsey);The Chainsmokers",
//		"One;Dance;Drake",
//		"Treat You Better;Shawn Mendes",
//		"Don’t Let Me Down;The Chainsmokers",
//		"Unforgettable [Explicit];French Montana",
//		"Rockabye;Clean Bandit",
//		"Heathens;TWENTY ØNE PILØTS",
//		"Needed Me;Rihanna",
//		"Mettalica;enter sandman",
//		"coldplay;vivalavida"};
//		List<SongEntry> list = Arrays.stream(titles)
//				.map((s)->s.split(";"))
//				.map((s)->new SongEntry(s[0],s[1]))
//				.collect(Collectors.toList());
////		List<SongEntry> list = IntStream.range(1,50)
////				.mapToObj((i)-> new SongEntry("Artist "+i, "Song "+i))
////				.collect(Collectors.toList());
//		List<Broadcast> broadcasts = pr3Service.getSongs(Optional.empty());
//		broadcasts.stream().forEach(b -> System.out.println(b.getTitle()+" "+b.getSongEntries().size()));
////		service.searchConcurrently(list);
	}
}
