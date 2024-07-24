package com.github.vrnvu.java_todo_list.todos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TodoEntity {
    @Id
    public Long id;
    public String title;
    public Boolean completed;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
