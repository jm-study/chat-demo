package study.chat.chatDemo.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.exception.NotFoundException;
import study.chat.chatDemo.hello.model.Contents;
import study.chat.chatDemo.utils.CallApiCommon;

import java.util.Map;

@Service
@Slf4j
public class ContentsSevice {

    public Contents showMacroFromApiServer() {
        String apiServerUrl = "127.0.0.1:9080/macro";

        Map<String, Object> result =CallApiCommon.callApi(apiServerUrl, null);

        if(result == null) {
            throw new NotFoundException("api server return null");
        }

        Contents resultContents = new Contents().builder()
                .content((String) result.get("contents"))
                .build();

        return resultContents;
    }
}
