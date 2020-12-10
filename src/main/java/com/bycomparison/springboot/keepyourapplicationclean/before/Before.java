package com.bycomparison.springboot.keepyourapplicationclean.before;

import java.net.http.HttpClient;

public class Before {



}

@interface SpringBootApplication {}
@interface Bean {}
@interface EnableScheduling {}

// BEFORE:start
@SpringBootApplication
// START_HIGHLIGHT
@EnableScheduling
// END_HIGHLIGHT
class MarsMissionApplication {
  // START_HIGHLIGHT
  @Bean
  HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }
  // END_HIGHLIGHT
}
// BEFORE:end



