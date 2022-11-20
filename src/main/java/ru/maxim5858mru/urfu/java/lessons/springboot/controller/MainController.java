package ru.maxim5858mru.urfu.java.lessons.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;

@RestController
public class MainController {
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request) {
        var response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
