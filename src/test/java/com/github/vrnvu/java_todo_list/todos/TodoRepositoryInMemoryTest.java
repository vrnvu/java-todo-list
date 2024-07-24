package com.github.vrnvu.java_todo_list.todos;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryInMemoryTest {

    @Test
    void createTodoThenGet() {
        var todoRepository = new TodoRepositoryInMemory();
        var title = "title";
        todoRepository.createTodo(title);
        var todo = todoRepository.getTodo("0").get();

        assertNotNull(todo);
        assertEquals(todo.id(), "0");
        assertEquals(todo.title(), title);
        assertFalse(todo.completed());
    }

    @Test
    void getNotFound() {
        var todoRepository = new TodoRepositoryInMemory();
        Optional<Todo> todo = todoRepository.getTodo("not-found");

        assertFalse(todo.isPresent());
    }

    @Test
    void getAll() {
        var todoRepository = new TodoRepositoryInMemory();
        todoRepository.createTodo("title-id");
        List<Todo> todos = todoRepository.getTodos();
        assertNotNull(todos);
        assertEquals(todos.size(), 1);
    }
}