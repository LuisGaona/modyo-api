package com.modyo.app;

import com.modyo.app.service.PokemonService;
import com.modyo.app.service.impl.PokemonAsyncService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@OpenAPIDefinition(info = @Info(
        title = "Modyo Pokemon Microservices",
        version = "1.0.0",
        description = "API for get pokemon details"))
@Generated
@EnableAsync
@EnableCaching
@SpringBootApplication
public class MondyoApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MondyoApiApplication.class, args);
  }
}
