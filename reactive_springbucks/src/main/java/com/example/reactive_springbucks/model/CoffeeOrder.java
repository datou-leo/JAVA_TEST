package com.example.reactive_springbucks.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder implements Serializable {
    @Id
    private Long id;

    private String customer;

    private List<Coffee> items;

    private OrderState state;

    private Date createTime;

    private Date updateTime;

}
