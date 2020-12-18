package com.timecap.apiService.Dao;

import com.timecap.apiService.Do.ImageDo;
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
import java.util.List;
import java.util.Map;

@Repository
public class ImageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigInteger insert(ImageDo image){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String filename = image.getFile();
        jdbcTemplate.update(connection ->{
                PreparedStatement ps = connection.prepareStatement("insert into image(filename)values(?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, filename);
                return ps;
            }, keyHolder
        );
        return (BigInteger) keyHolder.getKey();
    }

    public ImageDo getImage(Long id){
        return jdbcTemplate.queryForObject(
                "select * from image where id=?",
                new RowMapper<ImageDo>() {
                    @Override
                    public ImageDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        ImageDo image = new ImageDo();
                        image.setId(resultSet.getLong("id"));
                        image.setFile(resultSet.getString("filename"));
                        return image;
                    }
                },
                id
        );
    }

}
