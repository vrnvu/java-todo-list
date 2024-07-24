package com.github.vrnvu.java_todo_list.todos;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.util.List;

@Controller
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping(
            value="/todos",
            consumes={"application/json"},
            produces={"application/json"}
    )
    public ResponseEntity<Void> createTodo(String title) {
        var todo = todoService.createTodo(title);
        var uri = URI.create(String.format("/todos/%s", todo.id()));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(
            value="/todos/{id}",
            consumes={"application/json"},
            produces={"application/json"}
    )
    public ResponseEntity<Todo> getTodo(@PathVariable String id) {
        var todo = todoService.getTodo(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(
            value="/todos",
            consumes={"application/json"},
            produces={"application/json"}
    )
    public ResponseEntity<List<Todo>> getTodos() {
        var todos = todoService.getTodos();
        return ResponseEntity.ok(todos);
    }
}
