package com.timecap.apiService.Service;

import com.timecap.apiService.Dao.TextDao;
import com.timecap.apiService.Do.TextDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class TextService {
    @Autowired
    private TextDao textDao;

    public BigInteger add(TextDo text){
        return textDao.insert(text);
    }

    public TextDo getText(Long id){
        return textDao.getText(id);
    }
}
