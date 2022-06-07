package ru.lanit.at.Sql;

import java.io.IOException;
import java.sql.*;

public class PostgreSql {

    private static final PostgreSql INSTANCE = new PostgreSql();

    private PostgreSql() {
    }

    public static PostgreSql getInstance() {
        return INSTANCE;
    }

    private Connection connectSql() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Connection dbConnection = null;
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("accountSql.properties"));
            dbConnection = DriverManager.getConnection(System.getProperty("url"), "tester", System.getProperty("tester"));
            return dbConnection;
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public ResultSet selectTableSql(String request) throws SQLException {
        Connection dbConnection = connectSql();
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(request);
        dbConnection.close();
        return resultSet;
    }


}
