package com.bullstra.logistic.authentication.client;

import com.bullstra.logistic.authentication.dto.UserAuthDataDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UsersCoreClient {

    // WebClient instance for making HTTP calls
    private final WebClient webClient;

    // Injects the base URL from application properties
    public UsersCoreClient(@Value("${users-core.base-url}") String usersCoreBaseUrl) {
        // Initializes the WebClient with the configured base URL
        this.webClient = WebClient.builder().baseUrl(usersCoreBaseUrl).build();
    }

    public Mono<UserAuthDataDTO> getAuthDataByEmail(String email){
        return webClient.get()
                // Start of GET request
                .uri(uriBuilder -> uriBuilder
                        .path("/api/users/internal/auth-data")
                        .queryParam("email", email)
                        .build())
                // Executes the request and retrieves the response body
                .retrieve()
                // Converts the response body to a Mono of UserAuthDataDTO
                .bodyToMono(UserAuthDataDTO.class);
    }
}