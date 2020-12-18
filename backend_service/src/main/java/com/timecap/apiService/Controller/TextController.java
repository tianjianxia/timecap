package com.timecap.apiService.Controller;

import com.timecap.apiService.Do.TextDo;
import com.timecap.apiService.Service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class TextController {
    @Autowired
    private TextService textService;

    @PostMapping("/text")
    public BigInteger add(@RequestBody TextDo text){
        return textService.add(text);
    }

    @GetMapping("/text/{id}")
    public TextDo getText(@PathVariable("id") Long id){
        return textService.getText(id);
    }
}
