package ru.lanit.at.db.datasource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.lanit.at.db.dto.Login;
import ru.lanit.at.db.mapper.LoginMapper;

public class LoginTable {
    private JdbcTemplate jdbcTemplate;

    private static final String selectLogin = "select * from \"loginShema\".\"loginTable\"";

    public LoginTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Login selectLogin() {
        try {
            return jdbcTemplate
                    .queryForObject(selectLogin, new LoginMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
