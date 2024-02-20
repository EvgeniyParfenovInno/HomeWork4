package ru.demo.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class User {
    private Long id;
    private String userName;
}
