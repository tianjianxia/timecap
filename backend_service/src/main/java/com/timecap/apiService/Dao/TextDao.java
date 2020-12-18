package com.timecap.apiService.Dao;

import com.timecap.apiService.Do.TextDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class TextDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigInteger insert(TextDo text){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String txt = text.getText();
        jdbcTemplate.update(connection ->{
                    PreparedStatement ps = connection.prepareStatement("insert into text(text)values(?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, txt);
                    return ps;
                }, keyHolder
        );
        return (BigInteger) keyHolder.getKey();
    }

    public TextDo getText(Long id){
        return jdbcTemplate.queryForObject(
                "select * from text where id=?",
                new RowMapper<TextDo>() {
                    @Override
                    public TextDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        TextDo text = new TextDo();
                        text.setId(resultSet.getLong("id"));
                        text.setText(resultSet.getString("text"));
                        return text;
                    }
                },
                id
        );
    }
}
