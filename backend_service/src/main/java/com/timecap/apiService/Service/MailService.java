package com.timecap.apiService.Service;

import com.timecap.apiService.Dao.MailDao;
import com.timecap.apiService.Do.MailDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailService {
    @Autowired
    private MailDao mailDao;

    public void add(MailDo mail){
        mailDao.insert(mail);
    }

    public void updateText(Long id, Long textId){
        mailDao.updateText(id, textId);
    }

    public void updateImage(Long id, Long imageId){
        mailDao.updateImage(id, imageId);
    }

    public String getCurrent(){
        return mailDao.getCurrent();
    }

    public MailDo getMail(Long id){
        return mailDao.getMail(id);
    }


}
