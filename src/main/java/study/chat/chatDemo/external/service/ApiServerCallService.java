package study.chat.chatDemo.external.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.external.dto.*;
import study.chat.chatDemo.hello.model.Chat;

@Slf4j
@Service
public class ApiServerCallService extends AbstractCommonApiCallService {
    @Value("${study.api.server.url}")
    private String apiUrl;

    public ApiServerCallService() {super.API_NAME = "APISERVER";}

    public String getMacroContents(GetMacroRequest request) {
        String url = "http://" + apiUrl + "/macro";
        GetMacroResponse response = super.callPostApiByRestTemplete(url, GetMacroResponse.class, request);
        return response.getContents();
    }

//    public String getMacroContentsWithUserInfo(GetMacroRequest request) {
//        String url = "http://" + apiUrl + "/macro/"+request.getUserId();
//        GetMacroResponse2 response = super.callPostApiByRestTemplete(url, GetMacroResponse2.class, request);
//        return response.getContent();
//    }

    public String getUserInfo(GetUserInfoRequest request){
        String url = "http://" + apiUrl + "/getUserInfo/" + request.getUserId();
        GetUserInfoResponse response = super.callGetApiByRestTemplete(url, GetUserInfoResponse.class, request);

        return response.getNicName();
    }
}
