package com.example.mybatiscrud;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IFoodMapper {

    public List<Food> findAll();

    public void insertByFood(Food food);

    public void update(Food food);

    public void delete(int id);

    public Food findById(int id);
}
