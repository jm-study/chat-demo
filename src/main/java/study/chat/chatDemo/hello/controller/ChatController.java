package study.chat.chatDemo.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import study.chat.chatDemo.hello.model.Contents;
import study.chat.chatDemo.hello.model.HelloMessage;
import study.chat.chatDemo.hello.model.Chat;
import study.chat.chatDemo.hello.service.ContentsSevice;

@Controller
public class ChatController {

    @Autowired
    private ContentsSevice contentsSevice;

    @MessageMapping("/study/chat/chatDemo/hello")
    @SendTo("/topic/greetings")
    public Contents greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Contents(HtmlUtils.htmlEscape(message.getName()) + "���� �α����ϼ̽��ϴ�.");
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Chat chat(Chat chat) throws Exception {
        System.out.println("In Controller");
        return new Chat(chat.getName(), chat.getMessage());
    }

    @MessageMapping("/macro")
    @SendTo("/topic/greetings")
    public Contents macroGreeting() {
        return contentsSevice.showMacroFromApiServer();
    }

}