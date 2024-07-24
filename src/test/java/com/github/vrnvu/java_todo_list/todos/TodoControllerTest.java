package com.github.vrnvu.java_todo_list.todos;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoControllerTest {

    TodoRepository todoRepository = new TodoRepositoryInMemory();
    TodoService todoService = new TodoService(todoRepository);

    @Test
    void createTodoSuccess() {
        TodoController todoController = new TodoController(todoService);
        var title = "some foo bar baz";
        ResponseEntity<Void> response = todoController.createTodo(title);

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(response.getHeaders().getLocation(), URI.create("/todos/0"));
    }


    @Test
    void getTodoSuccess() {
        TodoController todoController = new TodoController(todoService);
        var title = "some foo bar baz";
        todoController.createTodo(title);

        ResponseEntity<Todo> response = todoController.getTodo("0");

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        var expected = new Todo("0", title, false);
        var got = response.getBody();
        assertNotNull(got);
        assertEquals(expected.id(), got.id());
        assertEquals(expected.title(), got.title());
        assertEquals(expected.completed(), got.completed());
    }

    @Test
    void getTodoNotFound() {
        TodoController todoController = new TodoController(todoService);
        ResponseEntity<Todo> response = todoController.getTodo("not found id");

        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
    }


    @Test
    void getTodosSuccessIsEmpty() {
        TodoController todoController = new TodoController(todoService);
        ResponseEntity<List<Todo>> response = todoController.getTodos();

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getTodosSuccessHasOneElement() {
        TodoController todoController = new TodoController(todoService);
        todoController.createTodo("id");
        ResponseEntity<List<Todo>> response = todoController.getTodos();

        assertNotNull(response);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}