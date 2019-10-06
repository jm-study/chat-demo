package study.chat.chatDemo.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.exception.NotFoundException;
import study.chat.chatDemo.hello.model.User;
import study.chat.chatDemo.utils.CallApiCommon;

import java.util.Map;

@Service
@Slf4j
public class UserService {

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
}
