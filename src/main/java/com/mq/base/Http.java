package com.mq.base;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

@Component
public class Http {
    @Resource
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> getForEntity(String domain, Map<String, Object> params, Class<T> entityType) {
        StringBuffer buff = new StringBuffer(domain);
        if (params != null && !params.isEmpty()) {
            buff.append("?");
        }
        String map2param = map2param(params);
        buff.append(map2param);
        URI uri = URI.create(buff.toString());
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(uri, entityType);
        return responseEntity;
    }

    public <T> ResponseEntity<T> postForEntity(String domain, Map<String, Object> params, Class<T> entityType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(params, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(domain, entity, entityType);
        return responseEntity;
    }

    public String map2param(Map<String, Object> params) {
        return map2param(params, "&");
    }

    public String map2param(Map<String, Object> params, String delimiter) {
        String result = "";
        if (params != null && !params.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            int index = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
                if (++index != params.size()) {
                    buffer.append(delimiter);
                }
            }
            result = buffer.toString();
        }
        return result;
    }
}
