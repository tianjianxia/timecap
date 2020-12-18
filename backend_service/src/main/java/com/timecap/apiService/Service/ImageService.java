package com.timecap.apiService.Service;

import com.timecap.apiService.Dao.ImageDao;
import com.timecap.apiService.Do.ImageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageDao imageDao;

    public BigInteger add(ImageDo image){
        return imageDao.insert(image);
    }

    public ImageDo getImage(Long id){
        return imageDao.getImage(id);
    }
}
