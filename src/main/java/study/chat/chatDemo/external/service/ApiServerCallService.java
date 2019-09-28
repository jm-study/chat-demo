package study.chat.chatDemo.external.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import study.chat.chatDemo.external.dto.GetMacroRequest;
import study.chat.chatDemo.external.dto.GetMacroResponse;

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
}
