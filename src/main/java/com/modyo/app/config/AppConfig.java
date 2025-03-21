package com.modyo.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executor;

@Configuration
public class AppConfig {
  private final String baseUrlPokemonApi;

  public AppConfig(@Value("${pokemon.api.base-url}") String baseUrlPokemonApi) {
    this.baseUrlPokemonApi = baseUrlPokemonApi;
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.rootUri(baseUrlPokemonApi).build();
  }

  @Bean
  public Executor  taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("async-task-");
    executor.initialize();
    return executor;
  }
}
