package study.chat.chatDemo.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.exception.NotFoundException;
import study.chat.chatDemo.external.dto.GetMacroRequest;
import study.chat.chatDemo.external.dto.GetUserInfoRequest;
import study.chat.chatDemo.external.dto.GetUserInfoResponse;
import study.chat.chatDemo.external.service.ApiServerCallService;
import study.chat.chatDemo.hello.model.User;
import study.chat.chatDemo.utils.CallApiCommon;

import java.util.Map;

@Service
@Slf4j
public class UserService {


    @Autowired
    private ApiServerCallService apiServerCallService;

    public User showUserInfoFromApiServer(String userId) {
        System.out.println("showUserInfoFromApiServer");

        String apiServerUrl = "http://127.0.0.1:9080/getUserInfo/"+userId;

        Map<String, Object> result = CallApiCommon.callGetApi(apiServerUrl, null);

        if(result == null) {
            throw new NotFoundException("api server return null");
        }

        User resultUser = new User().builder()
                .id((String)  result.get("id"))
                .nicName((String) result.get("nicName"))
                .build();

        return resultUser;
    }

    public String showMacroFromApiServerByUserId (String userId) {
        //TODO valid check

        return apiServerCallService.getUserInfo(
                GetUserInfoRequest.builder()
                        .userId(userId)
                        .build()
        );
    }


}
