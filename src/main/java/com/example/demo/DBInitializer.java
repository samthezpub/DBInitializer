package com.example.demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

@Slf4j
public class DBInitializer{

    private Connection connection;
    private DBInitializer() {
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
            this.connection = DriverManager.getConnection(
                    properties.getProperty("spring.datasource.url"),
                    properties.getProperty("spring.datasource.username"),
                    properties.getProperty("spring.datasource.password")
                    );
        } catch (SQLException e) {
            log.error("Не удалось получить доступ к базе данных");
        }
    }

    public ResultSet selectAllUsers(){
        final String SQL = "SELECT * FROM users";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            log.error("Ошибка получения данных из таблицы user");
            throw new Error(e.getMessage());
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Не удалось закрыть подключение к базе данных!");
        }
    }

}
