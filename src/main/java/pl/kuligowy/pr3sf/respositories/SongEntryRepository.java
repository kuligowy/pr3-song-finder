package pl.kuligowy.pr3sf.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kuligowy.pr3sf.domain.SongEntry;

public interface SongEntryRepository extends JpaRepository<SongEntry,Long> {

}
