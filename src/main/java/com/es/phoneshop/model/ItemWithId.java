package com.es.phoneshop.model;

public abstract class ItemWithId {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
