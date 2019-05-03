package com.mq.base;

import com.mq.util.MapUtil;
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
        String map2param = MapUtil.map2param(params);
        buff.append(map2param);
        URI uri = URI.create(buff.toString());
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(uri, entityType);
        return responseEntity;
    }

    public <T> ResponseEntity<T> postForEntity(String domain, Object params, Class<T> entityType, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity(params, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(domain, entity, entityType);
        return responseEntity;
    }
}
