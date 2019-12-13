package com.example.mysqljdbctemplate;

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
import java.util.List;

@Slf4j
@Repository
public class StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //添加
    public void insertData(ArrayList<Student> students){
        students.forEach(student->{
            jdbcTemplate.update("INSERT INTO STUDENT (ID,NAME) VALUES(?,?)",student.getId(),student.getName());
        });
    }

    //查询
    public void listData(){
        List<Student> list =jdbcTemplate.query("SELECT ID,NAME FROM STUDENT",  new RowMapper<Student>(){

            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                return Student.builder().id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build();
            }
        });
        list.forEach(student->log.info("STUDENT:{}",student));
    }

    //修改
    public void updateData(Student student){
        jdbcTemplate.update("UPDATE STUDENT SET NAME=? WHERE ID=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,student.getName());
                preparedStatement.setInt(2,student.getId());
            }
        });
    }

    //删除
    public void deleteData(Student student){
        jdbcTemplate.update("DELETE FROM STUDENT WHERE ID=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,student.getId());
            }
        });
    }
}
