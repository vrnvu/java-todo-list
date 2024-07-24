package com.github.vrnvu.java_todo_list.todos;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    @Test
    void createTodo() {
        var repository = Mockito.mock(TodoRepository.class);
        var title = "title";
        var expected = new Todo("0", title, false);
        when(repository.createTodo(title)).thenReturn(expected);

        var service = new TodoService(repository);
        Todo got = service.createTodo(title);

        assertSame(expected, got);
    }

    @Test
    void getTodo() {
        var repository = Mockito.mock(TodoRepository.class);
        var id = "0";
        var title = "title";
        var expected = new Todo(id, title, false);
        when(repository.getTodo(id)).thenReturn(Optional.of(expected));

        var service = new TodoService(repository);
        var got = service.getTodo(id);
        assertTrue(got.isPresent());
        assertSame(expected, got.get());
    }

    @Test
    void getTodosEmpty() {
        var repository = Mockito.mock(TodoRepository.class);
        when(repository.getTodos()).thenReturn(List.of());

        var service = new TodoService(repository);
        var got = service.getTodos();
        assertTrue(got.isEmpty());
    }


    @Test
    void getTodosOneTodo() {
        var repository = Mockito.mock(TodoRepository.class);
        var id = "0";
        var title = "title";
        var expected = new Todo(id, title, false);
        when(repository.getTodos()).thenReturn(List.of(expected));

        var service = new TodoService(repository);
        var got = service.getTodos();
        assertEquals(1, got.size());
        assertSame(expected, got.getFirst());
    }
}