package com.example.jdbctemplate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class People {
    private int id;
    private String name;

}
