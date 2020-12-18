package com.timecap.apiService.Service;

import com.timecap.apiService.Dao.UserDao;
import com.timecap.apiService.Do.MailDo;
import com.timecap.apiService.Do.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void add(UserDo user){
        userDao.insert(user);
    }

    public UserDo getUser(String gmail){
        return userDao.getUser(gmail);
    }

    public List<MailDo> getMails(String gmail){
        return userDao.getMails(gmail);
    }

    public void setPurchaseda(String gmail){
        userDao.setPurchasea(gmail);
    }

    public void setPurchasedb(String gmail){
        userDao.setPurchaseb(gmail);
    }

    public int totalSend(String gmail){return userDao.totalSend(gmail);}

    public Boolean check(String gmail){
        return userDao.check(gmail);
    }
}
