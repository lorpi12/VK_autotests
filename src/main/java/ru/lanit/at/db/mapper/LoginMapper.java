package ru.lanit.at.db.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.lanit.at.db.dto.Login;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMapper implements RowMapper<Login> {

    @Override
    public Login mapRow(ResultSet resultSet, int i) throws SQLException {
        Login lo = new Login();
        lo.setId(resultSet.getInt("id"));
        lo.setLogin(resultSet.getString("login"));
        lo.setPassword(resultSet.getString("password"));
        lo.setToken(resultSet.getString("token"));
        return lo;
    }
}
