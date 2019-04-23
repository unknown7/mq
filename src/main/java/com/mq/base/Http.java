package com.mq.base;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class Http {
    @javax.annotation.Resource
    private RestTemplate restTemplate;

    public String post2String(String url, Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(params, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        return responseEntity.getBody();
    }

    public Resource post2Resource(String url, Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(params, headers);
        ResponseEntity<Resource> responseEntity = restTemplate.postForEntity(url, entity, Resource.class);
        return responseEntity.getBody();
    }

    public String get(String url, Map<String, Object> params) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        return responseEntity.getBody();
    }

    public String map2str(Map<String, Object> map) {
        return map2str(map, "&");
    }

    public String map2str(Map<String, Object> map, String delimiter) {
        String result = "";
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            int index = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
                if (++index != map.size()) {
                    buffer.append(delimiter);
                }
            }
            result = buffer.toString();
        }
        return result;
    }
}
