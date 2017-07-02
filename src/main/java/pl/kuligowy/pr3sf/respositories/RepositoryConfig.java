package pl.kuligowy.pr3sf.respositories;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

/**
 * Created by coolig on 02.07.17.
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class RepositoryConfig {
}
