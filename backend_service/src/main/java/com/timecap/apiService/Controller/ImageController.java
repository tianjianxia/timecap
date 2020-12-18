package com.timecap.apiService.Controller;

import com.timecap.apiService.Do.ImageDo;
import com.timecap.apiService.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/image")
    public BigInteger add(@RequestBody ImageDo image){
        return imageService.add(image);
    }

    @GetMapping("/image/{id}")
    public ImageDo getImage(@PathVariable("id") Long id){
        return imageService.getImage(id);
    }

}
