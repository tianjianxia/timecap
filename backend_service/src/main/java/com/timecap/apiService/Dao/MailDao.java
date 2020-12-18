package com.timecap.apiService.Dao;

import com.timecap.apiService.Do.MailDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

@Repository
public class MailDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insert(MailDo mail){
        Long textId = mail.getText();
        Long imageId = mail.getImage();
        String fromUser = mail.getFromUser();
        String toUser = mail.getToUser();
        Date sendDate = mail.getSendDate();
        Date openDate = mail.getOpenDate();
        jdbcTemplate.update(
                "insert into mail(textId,imageId,fromUser,toUser,sendDate,openDate)values(?,?,?,?,?,?)",
                textId,
                imageId,
                fromUser,
                toUser,
                sendDate,
                openDate
        );
    }

    public void updateText(Long id, Long textId){
        jdbcTemplate.update("UPDATE mail SET textId=? WHERE id=?", textId, id);
    }

    public void updateImage(Long id, Long imageId){
        jdbcTemplate.update("UPDATE mail SET imageId=? WHERE id=?", imageId, id);
    }

    public String getCurrent(){
        return jdbcTemplate.query("SELECT * from mail ORDER BY id DESC LIMIT 1", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                return rs.next() ? rs.getString("id") : null;
            }
        });
        /*return jdbcTemplate.queryForObject(
                "SELECT * from mail ORDER BY id DESC LIMIT 1",
                new RowMapper<MailDo>() {
                    @Override
                    public MailDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        MailDo mail = new MailDo();
                        mail.setId(resultSet.getLong("id"));
                        mail.setText(resultSet.getLong("textId"));
                        mail.setImage(resultSet.getLong("imageId"));
                        mail.setFromUser(resultSet.getString("fromUser"));
                        mail.setToUser(resultSet.getString("toUser"));
                        mail.setSendDate(resultSet.getDate("sendDate"));
                        mail.setOpenDate(resultSet.getDate("openDate"));
                        return mail;
                    }
                }
        );*/
    }

    public MailDo getMail(Long id){
        return jdbcTemplate.queryForObject(
                "select * from mail where id=?",
                new RowMapper<MailDo>() {
                    @Override
                    public MailDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        MailDo mail = new MailDo();
                        mail.setId(resultSet.getLong("id"));
                        mail.setText(resultSet.getLong("textId"));
                        mail.setImage(resultSet.getLong("imageId"));
                        mail.setFromUser(resultSet.getString("fromUser"));
                        mail.setToUser(resultSet.getString("toUser"));
                        mail.setSendDate(resultSet.getDate("sendDate"));
                        mail.setOpenDate(resultSet.getDate("openDate"));
                        return mail;
                    }
                },
                id
        );
    }

}
