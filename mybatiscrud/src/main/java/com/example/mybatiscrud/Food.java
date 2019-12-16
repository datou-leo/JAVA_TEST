package com.example.mybatiscrud;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//建表的语句:CREATE TABLE T_FOOD (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(64));

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {


    private int id;

    private String name;
}
