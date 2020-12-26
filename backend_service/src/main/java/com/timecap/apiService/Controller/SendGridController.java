package com.timecap.apiService.Controller;

import com.timecap.apiService.Service.SendGridService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SendGridController {
    @Autowired
    private SendGridService sendGridService;

    @PostMapping("/sendnew")
    public String send(@RequestBody String params) throws IOException, Exception {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(params);
        String toRep = json.get("to").toString();
        String contentRep = json.get("content").toString();
        String pathRep = json.get("path").toString();
        String dateString = json.get("date").toString();
        Date dateRep = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);

        return sendGridService.delay(toRep, contentRep, pathRep, dateRep);
    }

}
