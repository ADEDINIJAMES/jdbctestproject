package com.tumech.jdbcclient.initiallizer;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class DataBaseInitializer  implements CommandLineRunner {
//  @Autowired
//   private JdbcTemplate jdbcTemplate;
//  private Logger logger = LoggerFactory.getLogger(DataBaseInitializer.class);
//
//    @Override
//    public void run(String... args) throws Exception {
//        executeSchema("schema.sql");
//    }
//
//    public void executeSchema(String path){
//        try {
//            ClassPathResource resource = new ClassPathResource(path);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
//            String sql = reader.lines().collect(Collectors.joining("/n"));
//            jdbcTemplate.execute(sql);
//            logger.info("Executed successfully");
//
//        }catch (Exception e){
//e.printStackTrace();
//        }
//    }
//


@Component
class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        executeSqlScript("schema.sql");
    }

    private void executeSqlScript(String scriptPath) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ClassPathResource resource = new ClassPathResource(scriptPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String sql = reader.lines().collect(Collectors.joining("\n"));
            System.out.println("Executing SQL script: \n" + sql); // Print the SQL script

            // Split the script by semicolons to execute each statement separately
            String[] sqlStatements = sql.split(";");
            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    statement.execute(sqlStatement.trim());
                }
            }

            System.out.println("Executed SQL script: " + scriptPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
