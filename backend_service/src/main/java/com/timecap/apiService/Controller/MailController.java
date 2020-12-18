package com.timecap.apiService.Controller;

import com.timecap.apiService.Do.MailDo;
import com.timecap.apiService.Jenkins.JenkinsPost;
import com.timecap.apiService.Service.MailService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/mail")
    public void add(@RequestBody MailDo mail){
        mailService.add(mail);
    }

    @GetMapping("/mail/text/{id}/{textId}")
    public void updateText(@PathVariable("id") Long id, @PathVariable("textId") Long textId){
        mailService.updateText(id, textId);
    }

    @GetMapping("/mail/image/{id}/{imageId}")
    public void updateImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId){
        mailService.updateImage(id, imageId);
    }

    @GetMapping("/mail/current")
    public String getCurrent(){
        return mailService.getCurrent();
    }

    @GetMapping("/mail/{id}")
    public MailDo getMail(@PathVariable("id") Long id){
        return mailService.getMail(id);
    }

    @PostMapping("/mail/jenkins")
    public void jenkins(@RequestBody String params) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(params);
        String from = json.get("from").toString();
        String to = json.get("to").toString();
        String content = json.get("content").toString();
        String path = json.get("path").toString();
        String opendate = json.get("opendate").toString();
        JenkinsPost jp = new JenkinsPost();
        jp.post(from, to, content, path, opendate);
    }
}
