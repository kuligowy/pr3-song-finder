package pl.kuligowy.pr3sf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kuligowy.pr3sf.domain.SongEntry;
import pl.kuligowy.pr3sf.services.YoutubeFinderService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Pr3SfApplication {

	@Autowired
	YoutubeFinderService service;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Pr3SfApplication.class, args);

		Pr3SfApplication app =ctx.getBean(Pr3SfApplication.class);
		app.enjoy();;

	}


	public void enjoy(){
		List<SongEntry> list = IntStream.range(1,50)
				.mapToObj((i)-> new SongEntry("Artist "+i, "Song "+i))
				.collect(Collectors.toList());
		service.find(list);
	}
}
