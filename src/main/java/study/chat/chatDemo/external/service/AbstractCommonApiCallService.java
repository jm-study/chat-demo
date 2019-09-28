package study.chat.chatDemo.external.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import study.chat.chatDemo.exception.HttpException;

import java.util.Optional;

@Slf4j
public abstract class AbstractCommonApiCallService {

    protected String API_NAME;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.requestFactory(new ClientHttpRequestFactorySupplier()).build();
    }

    protected <RESULT_DTO> RESULT_DTO callPostApiByRestTemplete(String url, Class<RESULT_DTO> responseType, Object requestDto) {
        try {
            log.info("API CALLED url =" +url);

            ResponseEntity<RESULT_DTO> responseEntity = restTemplate.exchange(url,
                    HttpMethod.POST, new HttpEntity<>(requestDto), responseType);

            Optional<RESULT_DTO> optional = Optional.ofNullable(responseEntity.getBody());
            if(optional.isPresent()) {
                log.debug("api Call result => " + optional.get());
                //TODO check error ( code or something else )
            }
            return optional.get();
        } catch (HttpStatusCodeException e) {
            log.error("HTTP status error {}", e.getCause());
            throw new HttpException(e.getCause());
        }
    }


    protected <RESULT_DTO> RESULT_DTO callGetApiByRestTemplete(String url, Class<RESULT_DTO> responseType, Object requestDto) {
        try {
            log.info("API CALLED url =" +url);

            ResponseEntity<RESULT_DTO> responseEntity = restTemplate.exchange(url,
                    HttpMethod.GET, new HttpEntity<>(requestDto), responseType);

            Optional<RESULT_DTO> optional = Optional.ofNullable(responseEntity.getBody());
            if(optional.isPresent()) {
                log.debug("api Call result => " + optional.get());
                //TODO check error ( code or something else )
            }
            return optional.get();
        } catch (HttpStatusCodeException e) {
            log.error("HTTP status error {}", e.getCause());
            throw new HttpException(e.getCause());
        }
    }

}
