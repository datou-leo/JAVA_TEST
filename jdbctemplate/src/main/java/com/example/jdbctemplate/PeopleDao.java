package com.example.jdbctemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class PeopleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加
    public void insertData(ArrayList<People> peoples){
        peoples.forEach(people->{
            jdbcTemplate.update("INSERT INTO PEOPLE (ID,NAME) VALUES(?,?)",people.getId(),people.getName());
        });
    }

    //查询
    public void listData(){
        List<People> list =jdbcTemplate.query("SELECT ID,NAME FROM PEOPLE",  new RowMapper<People>(){

            @Override
            public People mapRow(ResultSet resultSet, int i) throws SQLException {
                return People.builder().id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build();
            }
        });
        list.forEach(people->log.info("PEOPLE:{}",people));
    }

    //修改
    public void updateData(People people){
        jdbcTemplate.update("UPDATE PEOPLE SET NAME=? WHERE ID=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,people.getName());
                preparedStatement.setInt(2,people.getId());
            }
        });
    }

    //删除
    public void deleteData(People people){
        jdbcTemplate.update("DELETE FROM PEOPLE WHERE ID=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,people.getId());
            }
        });
    }
}
