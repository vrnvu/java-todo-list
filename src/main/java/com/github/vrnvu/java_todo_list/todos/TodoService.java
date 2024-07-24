package com.github.vrnvu.java_todo_list.todos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(String title) {
        return todoRepository.createTodo(title);
    }

    public Optional<Todo> getTodo(String id) {
        return todoRepository.getTodo(id);
    }

    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }
}
