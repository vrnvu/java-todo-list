package com.github.vrnvu.java_todo_list.todos;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;

@SpringBootTest
@AutoConfigureWebTestClient
public class TodoIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void createTodoSuccess() {
        String title = "title";

        webClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"title\": \"" + title + "\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valuesMatch("Location", "/todos/[0-9]+");
    }

    @Test
    void getTodoSuccess() {
        String title = "some title";

        String locationHeader = webClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"title\": \"" + title + "\"}")
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Void.class)
                .getResponseHeaders()
                .getFirst(HttpHeaders.LOCATION);

        assert locationHeader != null;
        webClient.get()
                .uri(URI.create(locationHeader))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.completed").isEqualTo(false);
    }

    @Test
    void getTodoNotFound() {
        webClient.get()
                .uri("/todos/not-found-id")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getTodosSuccessIsEmpty() {
        webClient.get()
                .uri("/todos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEmpty();
    }

    @Test
    void getTodosSuccessHasOneElement() {
        String title = "some content";

        webClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"title\": \"" + title + "\"}")
                .exchange()
                .expectStatus().isCreated();

        webClient.get()
                .uri("/todos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo(title)
                .jsonPath("$[0].completed").isEqualTo(false);
    }
}
