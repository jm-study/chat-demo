package study.chat.chatDemo.hello;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {


    @MessageMapping("/study/chat/chatDemo/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getName()) + "���� �α����ϼ̽��ϴ�.");
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Chat chat(Chat chat) throws Exception {
        System.out.println("In Controller");
        return new Chat(chat.getName(), chat.getMessage());
    }

}