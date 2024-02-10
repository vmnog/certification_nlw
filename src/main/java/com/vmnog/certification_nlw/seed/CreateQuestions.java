package com.vmnog.certification_nlw.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateQuestions {
    private final JdbcTemplate jdbcTemplate;

    @SuppressWarnings("null")
    public CreateQuestions(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pg_nlw");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        CreateQuestions createQuestions = new CreateQuestions(dataSource);
        createQuestions.run(args);
    }

    public void run(String... args) {
        executeSqlFile("src/main/resources/create-questions.sql");
    }

    private void executeSqlFile(String filePath) {
        try {
            String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));

            jdbcTemplate.execute(sqlScript);

            System.out.println("SQL script executed successfully!");
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + e.getMessage());
            e.printStackTrace();
        }
    }
}
