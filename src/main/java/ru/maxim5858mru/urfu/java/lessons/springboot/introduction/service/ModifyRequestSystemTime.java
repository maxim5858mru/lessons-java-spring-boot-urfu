package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Request;

@Service
public class ModifyRequestSystemTime implements ModifyRequestService {
    @Override
    public void modifyRequest(Request request) {
        request.setSystemTime("Test");

        var httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:9090/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
