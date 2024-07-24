package com.github.vrnvu.java_todo_list.todos;

import org.junit.jupiter.api.Test;

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
}