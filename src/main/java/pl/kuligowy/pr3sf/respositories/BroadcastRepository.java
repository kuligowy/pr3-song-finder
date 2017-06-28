package pl.kuligowy.pr3sf.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kuligowy.pr3sf.domain.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast,Long>,JpaSpecificationExecutor {



}
