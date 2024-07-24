package com.github.vrnvu.java_todo_list.todos;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository {
    Todo createTodo(String title);
    Optional<Todo> getTodo(String id);
    List<Todo> getTodos();
}
