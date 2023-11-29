package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DBInitializer{
    public DBInitializer(){
        init();
    }

    private void init() {
        Reader reader = null;
        try {
            reader = new FileReader("src/main/resources/application.properties");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage() + "\n" + e.getCause());
        }

        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            log.error("Не удалось прочитать файл Properties");
        }

        try {
            Connection conn = DriverManager.getConnection(
                    properties.getProperty("spring.datasource.url"),
                    properties.getProperty("spring.datasource.username"),
                    properties.getProperty("spring.datasource.password")
                    );
        } catch (SQLException e) {
            log.error("Не удалось получить доступ к базе данных");
        }
    }
}
