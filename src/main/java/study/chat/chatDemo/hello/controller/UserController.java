package study.chat.chatDemo.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import study.chat.chatDemo.hello.model.User;
import study.chat.chatDemo.hello.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @MessageMapping("/login")
    @SendTo("/topic/login")
    public boolean login(String userId){
        User user = userService.showUserInfoFromApiServer(userId);

        if (null == user){
            System.out.println("1");
            return false;
        } else {
            System.out.println("2");
            return true;
        }
    }
}
