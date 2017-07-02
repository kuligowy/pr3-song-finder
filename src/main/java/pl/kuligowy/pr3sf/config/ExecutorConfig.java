package pl.kuligowy.pr3sf.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.*;

import java.util.concurrent.*;

@Configuration
public class ExecutorConfig {

    @Bean(name ="commonExecutor")
    public Executor commonExecutor(){
//        ThreadPoolExecutor executor =Executors.newFixedThreadPool(1);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("Common-");
        executor.initialize();
        executor.setRejectedExecutionHandler((r, executor1) -> System.out.println("discarding task"));
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
