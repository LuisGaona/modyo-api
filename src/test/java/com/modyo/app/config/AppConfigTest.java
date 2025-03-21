package com.modyo.app.config;

import com.modyo.app.controller.PokemonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class AppConfigTest {

  private AppConfig appConfig;
  @Mock
  private RestTemplateBuilder restTemplateBuilder;

  @BeforeEach
  void setup() {
    appConfig = new AppConfig("https://pokeapi.co/api/v2");
  }

  @Test
  void testRestTemplate() {
    String baseUrl = "https://pokeapi.co/api/v2";
    when(restTemplateBuilder.rootUri(anyString())).thenReturn(restTemplateBuilder);
    when(restTemplateBuilder.build()).thenReturn(mock(RestTemplate.class));

    RestTemplate restTemplate = appConfig.restTemplate(restTemplateBuilder);

    assertNotNull(restTemplate);
    verify(restTemplateBuilder, times(1)).rootUri(baseUrl);
    verify(restTemplateBuilder, times(1)).build();
  }

  @Test
  void testTaskExecutor() {
    Executor executor = appConfig.taskExecutor();
    assertNotNull(executor);
    assertTrue(executor instanceof ThreadPoolTaskExecutor);
  }
}
