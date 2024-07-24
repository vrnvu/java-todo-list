package com.github.vrnvu.java_todo_list.todos;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepositoryInMemory implements TodoRepository {
    private final HashMap<String, Todo> database;

    public TodoRepositoryInMemory() {
        database = new HashMap<>();
    }

    @Override
    public Todo createTodo(String title) {
        Todo created = new Todo(String.valueOf(database.size()), title, false);
        database.put(created.id(), created);
        return created;
    }

    @Override
    public Optional<Todo> getTodo(String id) {
        return database.containsKey(id) ? Optional.of(database.get(id)) : Optional.empty();
    }

    @Override
    public List<Todo> getTodos() {
        return database.values().stream().toList();
    }
}
