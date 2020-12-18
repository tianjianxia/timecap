package com.timecap.apiService.Controller;

import com.timecap.apiService.Do.MailDo;
import com.timecap.apiService.Do.UserDo;
import com.timecap.apiService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public void add(@RequestBody UserDo user){
        userService.add(user);
    }

    @GetMapping("/user/{gmail}")
    public UserDo getUser(@PathVariable("gmail") String gmail){
        return userService.getUser(gmail);
    }

    @GetMapping("/user/check/{gmail}")
    public Boolean checkUser(@PathVariable("gmail") String gmail){
        return userService.check(gmail);
    }

    @GetMapping("/user/mail/{gmail}")
    public List<MailDo> getMails(@PathVariable("gmail") String gmail){
        return userService.getMails(gmail);
    }

    @GetMapping("/user/a/{gmail}")
    public void setPurchasedA(@PathVariable("gmail") String gmail){
        userService.setPurchaseda(gmail);
    }

    @GetMapping("/user/b/{gmail}")
    public void setPurchasedB(@PathVariable("gmail") String gmail){
        userService.setPurchasedb(gmail);
    }

    @GetMapping("/user/count/{gmail}")
    public int totalSend(@PathVariable("gmail") String gmail){return userService.totalSend(gmail);}
}
