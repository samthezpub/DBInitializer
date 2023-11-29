package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringApplication.run(DemoApplication.class, args);

        DBInitializer dbInitializer = context.getBean("dbInitializer", DBInitializer.class);

        ResultSet resultSet = dbInitializer.selectAllUsers();
        List<User> users = new LinkedList<>();
        try {
            while (resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        users.forEach(System.out::println);

        dbInitializer.close();
    }

}
