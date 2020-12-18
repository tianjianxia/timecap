package com.timecap.apiService.Dao;

import com.timecap.apiService.Do.MailDo;
import com.timecap.apiService.Do.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(UserDo user){
        String gmail = user.getUserGmail();
        Boolean isPurchaseda = user.getPurchaseda();
        Boolean isPurchasedb = user.getPurchasedb();
        jdbcTemplate.update(
                "insert into user(gmail,isPurchaseda,isPurchasedb)values(?,?,?)",
                gmail,
                isPurchaseda,
                isPurchasedb
        );
    }

    public UserDo getUser(String gmail){

        return jdbcTemplate.queryForObject(
                "select * from user where gmail=?",
                new RowMapper<UserDo>() {
                    @Override
                    public UserDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        UserDo user = new UserDo();
                        user.setUserGmail(resultSet.getString("gmail"));
                        user.setPurchaseda(resultSet.getBoolean("isPurchaseda"));
                        user.setPurchasedb(resultSet.getBoolean("isPurchasedb"));
                        return user;
                    }
                    },
                gmail
        );
    }

    public List<MailDo> getMails(String gmail){
        return jdbcTemplate.query(
                "select * from mail where fromUser=?",

                new RowMapper<MailDo>() {
                    @Override
                    public MailDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        MailDo md = new MailDo();
                        md.setId(resultSet.getLong("id"));
                        md.setText(resultSet.getLong("textId"));
                        md.setImage(resultSet.getLong("imageId"));
                        md.setFromUser(resultSet.getString("fromUser"));
                        md.setToUser(resultSet.getString("toUser"));
                        md.setSendDate(resultSet.getDate("sendDate"));
                        md.setOpenDate(resultSet.getDate("openDate"));
                        return md;
                    }
                },gmail
        );
    }

    public void setPurchasea(String gmail){
        jdbcTemplate.update(
                "update user set gmail=?,isPurchaseda=true",
                gmail
        );
    }

    public void setPurchaseb(String gmail){
        jdbcTemplate.update(
                "update user set gmail=?,isPurchasedb=true",
                gmail
        );
    }

    public int totalSend(String gmail){
        return jdbcTemplate.queryForObject("select count(fromUser) as total from mail where fromUser = \"" + gmail + "\"", Integer.TYPE);
    }

    public Boolean check(String gmail){
        List<UserDo> list =
                jdbcTemplate.query(
                "select * from user where gmail=?",

                new RowMapper<UserDo>() {
                    @Override
                    public UserDo mapRow(ResultSet resultSet, int i) throws SQLException {
                        UserDo ud = new UserDo();
                        ud.setUserGmail(resultSet.getString("gmail"));
                        ud.setPurchaseda(resultSet.getBoolean("isPurchaseda"));
                        ud.setPurchasedb(resultSet.getBoolean("isPurchasedb"));
                        return ud;
                    }
                },gmail
        );

        return list.size() != 0;
    }


}
