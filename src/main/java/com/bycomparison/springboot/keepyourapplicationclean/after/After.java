package com.bycomparison.springboot.keepyourapplicationclean.after;

import java.net.http.HttpClient;

public class After {

}

@interface SpringBootApplication {}
@interface Bean {}
@interface Configuration {}
@interface EnableScheduling {}

// AFTER:start
@SpringBootApplication
class MarsMissionApplication {
}

// START_HIGHLIGHT
@Configuration
class HttpClientConfiguration {
  @Bean
  HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }
}
// END_HIGHLIGHT

// START_HIGHLIGHT
@Configuration
@EnableScheduling
class SchedulingConfiguration {
}
// END_HIGHLIGHT
// AFTER:end


