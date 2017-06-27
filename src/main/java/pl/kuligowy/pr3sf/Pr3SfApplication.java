package pl.kuligowy.pr3sf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kuligowy.pr3sf.domain.Broadcast;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.services.PR3Service;
import pl.kuligowy.pr3sf.services.YoutubeFinderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
public class Pr3SfApplication {

	@Autowired
	YoutubeFinderService service;
	@Autowired
	PR3Service pr3Service;
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Pr3SfApplication.class, args);

		Pr3SfApplication app =ctx.getBean(Pr3SfApplication.class);
		app.enjoy();;

	}


	public void enjoy(){

		String[] titles = {
		"Closer (feat. Halsey);The Chainsmokers",
		"One;Dance;Drake",
		"Treat You Better;Shawn Mendes",
		"Don’t Let Me Down;The Chainsmokers",
		"Unforgettable [Explicit];French Montana",
		"Rockabye;Clean Bandit",
		"Heathens;TWENTY ØNE PILØTS",
		"Needed Me;Rihanna",
		"Mettalica;enter sandman",
		"coldplay;vivalavida"};
		List<SongEntry> list = Arrays.stream(titles)
				.map((s)->s.split(";"))
				.map((s)->new SongEntry(s[0],s[1]))
				.collect(Collectors.toList());
//		List<SongEntry> list = IntStream.range(1,50)
//				.mapToObj((i)-> new SongEntry("Artist "+i, "Song "+i))
//				.collect(Collectors.toList());
		List<Broadcast> broadcasts = pr3Service.getSongs(Optional.empty());
		broadcasts.stream().forEach(b -> System.out.println(b.getTitle()+" "+b.getSongEntries().size()));
//		service.searchConcurrently(list);
	}
}
