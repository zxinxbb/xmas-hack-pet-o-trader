package com.example.xmashack.domain;


public class PetOwner {

    private String id;
    private final String name;

    public PetOwner(String id, String name) {

        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
