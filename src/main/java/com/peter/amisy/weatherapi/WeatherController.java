package com.peter.amisy.weatherapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Controller
public class WeatherController {


    public final static String API = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    @Autowired
    private final WebClient webClient;

    public WeatherController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/weather")
    Mono<Object> getWeather(String location) {
        return webClient.get()
                .uri(API, location, "?key=${API_KEY}")
                .retrieve()
                .bodyToMono(Object.class)
                .onErrorMap(WebClientResponseException.class, ex -> {
                    if (ex.getRawStatusCode() == 404) {
                        return new LocationNotFoundException("Location not found for : " + location);
                    }
                    return ex;
                });
    }

}
