package study.chat.chatDemo.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.exception.NotFoundException;
import study.chat.chatDemo.external.dto.GetMacroRequest;
import study.chat.chatDemo.external.service.ApiServerCallService;
import study.chat.chatDemo.hello.model.Content;
import study.chat.chatDemo.utils.CallApiCommon;

import java.util.Map;

@Service
@Slf4j
public class ContentService {

    @Autowired
    private ApiServerCallService apiServerCallService;


    public Content showMacroFromApiServer(String userId) {
        System.out.println("showMacroFromApiServer");

        String apiServerUrl = "http://127.0.0.1:9080/macro/"+userId;

        Map<String, Object> result = CallApiCommon.callApi(apiServerUrl, null);

        if(result == null) {
            throw new NotFoundException("api server return null");
        }

        Content resultChat = new Content().builder()
                .nicName((String) result.get("nicName"))
                .message((String)  result.get("contents"))
                .build();

        return resultChat;
    }

    public String showMacroFromApiServerByContentsNo (String userId) {
        //TODO valid check

        return apiServerCallService.getMacroContentsWithUserInfo(
                GetMacroRequest.builder()
                        .userId(userId)
                        .build()
        );
    }
}
