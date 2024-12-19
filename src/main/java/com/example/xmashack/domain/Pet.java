package com.example.xmashack.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pet {
    private final String id;
    private final String name;
    private final String description;
    private final String type;
    private final Integer age;
    private final String imagePath;
    private final String petOwnerId;

    private byte[] image;

}
