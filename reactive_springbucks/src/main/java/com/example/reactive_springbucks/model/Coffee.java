package com.example.reactive_springbucks.model;

import com.example.reactive_springbucks.serialize.MoneyDeserialize;
import com.example.reactive_springbucks.serialize.MoneySerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Table("t_coffee")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    @Id
    private Long id;

    private String name;

    @JsonSerialize(using= MoneySerialize.class)
    @JsonDeserialize(using= MoneyDeserialize.class)
    private Money price;

    private Date createTime;

    private Date updateTime;

}
