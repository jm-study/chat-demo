package study.chat.chatDemo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import study.chat.chatDemo.exception.HttpException;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CallApiCommon {

    public static int HTTP_CONNECTION_TIMEOUT = 3 * 1000; // default time 을 5초로 설정
    public static int HTTP_CONNECTION_READ_TIMEOUT = 120 * 1000; // default read timeout 을 1분으로 설정

    public static Map<String ,Object> callApi(String url, Map<String, Object> messageBody) {
        if(url == null) {
            throw new HttpException("url is null");
        }

        try {
            URL callbackUrl = new URL(url);

            return callHttpsApi(callbackUrl, RequestMethod.POST, getJsonBody(messageBody));
        } catch (Exception e) {
            throw new HttpException(e);
        }

    }


    public static String getJsonBody(Map<String, Object> messageBody) {
        String jsonBody;
        try {
            jsonBody = new ObjectMapper()
                    .writeValueAsString(messageBody);
        } catch (Exception e) {
            throw new HttpException("ObjectMapper().writeValueAsString; variableMap="
                            + messageBody , e);
        }

        return jsonBody;
    }

    private static Map<String, Object> callHttpsApi(URL url, RequestMethod requestMethod, String jsonBody) {
        HttpURLConnection hcon;

        try {
            hcon = (HttpURLConnection) url.openConnection();
            hcon.setRequestMethod(requestMethod.toString());
            hcon.setConnectTimeout(HTTP_CONNECTION_TIMEOUT); // connection time out 설정
            hcon.setReadTimeout(HTTP_CONNECTION_READ_TIMEOUT);
            hcon.setDoInput(true); // Allow Inputs
            hcon.setUseCaches(false); // Don't use a cached copy.
            hcon.setAllowUserInteraction(false);

            hcon.setRequestProperty("User-Agent", "VPC Compute/1.0");
            hcon.setRequestProperty("Connection", "close");
            hcon.setRequestProperty("AcceptCharset", StandardCharsets.UTF_8.toString().toLowerCase());
            hcon.setRequestProperty("Content-Type", "application/json");



//            if (RequestMethod.POST == requestMethod) {
            hcon.setDoOutput(true); // Allow Outputs
            hcon.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON.toString() + "; charset="
                        + StandardCharsets.UTF_8.toString().toLowerCase());
//            }

            log.info("[callHttpsApi send]\t{\"url\":\"{}\", \"method\": \"{}\", \"request\":{}",
                    hcon.getURL(), requestMethod, jsonBody);

            hcon.connect(); // active-open TCP connection

        } catch (IOException e) {
            throw new HttpException("failed to openConnection url=" + url, e);
        }

        try {
            String jsonString = callConnectedHttpsApi(hcon, requestMethod, jsonBody);
            return jsonStringToMap(jsonString);

        } finally {
            hcon.disconnect();
        }
    }

    public static Map<String, Object> jsonStringToMap(String json) {
        Map<String, Object> resultMap;
        try {
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            log.trace("resultMap=>" + resultMap);
        } catch (Exception e) {
            throw new HttpException("failed to convert JSON to MAP. json=" + json);
        }
        return resultMap;
    }

    public static String callConnectedHttpsApi(HttpURLConnection hcon, RequestMethod requestMethod, String jsonBody) {

        BufferedReader reader = null;
        boolean isOpen = false;

        try {
            if (RequestMethod.POST == requestMethod) {
                BufferedOutputStream os = new BufferedOutputStream(hcon.getOutputStream());
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8.toString()));
                os.flush(); // send HTTP request
            }

            int responseCode = hcon.getResponseCode(); // NOTE: CANNOT send more data after get response code

            String responseCodeInString = "" + responseCode;
            String errorMessage = null;

            if (!responseCodeInString.startsWith("20")) {

                if (null != hcon.getErrorStream()) {

                    reader = new BufferedReader(new InputStreamReader(hcon.getErrorStream()), 1);

                    char[] buf = new char[1024];
                    StringBuffer sb = new StringBuffer();
                    int count = 0;
                    while (-1 != (count = reader.read(buf))) {
                        sb.append(buf, 0, count);
                    }

                    errorMessage = sb.toString();

                    log.error("[callConnectedHttpsApi ERROR]\t{\"url\":\"{}\", \"method\": \"{}\", \"request\":{}, \"errorMessage\": {}}",
                            hcon.getURL(), requestMethod, jsonBody, errorMessage);
                }

                throw new HttpException(errorMessage);
            }

            reader = new BufferedReader(new InputStreamReader(hcon.getInputStream()), 1);
            isOpen = true;

            char[] buf = new char[1024];
            StringBuffer sb = new StringBuffer();
            int count = 0;
            while (-1 != (count = reader.read(buf))) {
                sb.append(buf, 0, count);
            }

            String jsonResponse = sb.toString();

            reader.close();
            isOpen = false;

            log.info("[callConnectedHttpsApi Suc]\t{\"url\":\"{}\", \"method\": \"{}\", \"request\":{}, \"response\": {}}",
                    hcon.getURL(), requestMethod, jsonBody, jsonResponse);

            return jsonResponse;

        } catch (RuntimeException e) {

            if (null != reader && isOpen) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    throw new HttpException("failed to call api url=" + hcon.getURL() + "e1=" + e1);
                }
            }

            throw e;

        } catch (Exception e) {

            if (null != reader && isOpen) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    throw new HttpException("failed to call api url=" + hcon.getURL() + "e1=" + e1);
                }
            }

            throw new HttpException("failed to call api url=" + hcon.getURL() + "e=" + e);
        }

    }
}
