package com.example.rebboncustomerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class NewOrderRequest {
    private String customer;
    private List<String> items;
}
