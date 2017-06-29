package pl.kuligowy.pr3sf.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.*;

import java.util.concurrent.*;

@Configuration
public class ExecutorConfig {

    @Bean(name ="commonExecutor")
    public Executor commonExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Common-");
        executor.initialize();
        return executor;
    }
    @Bean(name = "youtubeExecutor")
    public Executor youtubeExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("YoutubeFinder-");
        executor.initialize();
        return executor;
    }


}
