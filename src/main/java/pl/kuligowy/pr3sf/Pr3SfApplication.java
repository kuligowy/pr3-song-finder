package pl.kuligowy.pr3sf;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.convert.threeten.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.scheduling.annotation.*;
import pl.kuligowy.pr3sf.respositories.*;

@SpringBootApplication
@ComponentScan
@EntityScan(
		basePackageClasses = { Pr3SfApplication.class, Jsr310JpaConverters.class }
)
@EnableAsync
@EnableScheduling
//@EnableWebMvc
//@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class,basePackages = "pl.kuligowy.pr3sf.respositories.*")
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
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
//		List<Broadcast> broadcasts = pr3Service.getFromSource(Optional.empty());
//		broadcasts.stream().forEach(b -> System.out.println(b.getTitle()+" "+b.getSongEntries().size()));
////		service.searchConcurrently(list);
	}
}
