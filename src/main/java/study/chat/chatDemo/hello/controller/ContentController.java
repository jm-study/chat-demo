package study.chat.chatDemo.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import study.chat.chatDemo.hello.model.Content;
import study.chat.chatDemo.hello.service.ContentService;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Content chat(Content chat) throws Exception {
        System.out.println("In Controller");
        return new Content(chat.getNicName(), chat.getMessage());
    }

    @MessageMapping("/macro")
    @SendTo("/topic/chat")
    public Content macroMessage(String userId) {
        Content chat = contentService.showMacroFromApiServer(userId);
        return new Content(chat.getNicName(), chat.getMessage());
    }
}