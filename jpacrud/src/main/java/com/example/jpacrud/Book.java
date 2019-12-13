package com.example.jpacrud;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//建表的语句:CREATE TABLE BOOK (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(64));

@Entity
@Table(name = "BOOK")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

}