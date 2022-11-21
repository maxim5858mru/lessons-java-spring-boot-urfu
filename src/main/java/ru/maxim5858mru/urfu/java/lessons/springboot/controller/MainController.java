package ru.maxim5858mru.urfu.java.lessons.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;
import ru.maxim5858mru.urfu.java.lessons.springboot.service.ModifyService;

@Slf4j
@RestController
public class MainController {
    private final ModifyService modifyService;

    @Autowired
    public MainController(@Qualifier("ModifyUid") ModifyService modifyService) {
//    public MainController(@Qualifier("ModifySystemTime") ModifyService modifyService) {
//    public MainController(@Qualifier("ModifyErrorMessage") ModifyService modifyService) {
        this.modifyService = modifyService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request) {
        // Логирование приходящего запроса
        log.info("Входящий запрос: " + String.valueOf(request));

        var response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        var responseAfterModify = modifyService.modify(response);

        return new ResponseEntity<>(responseAfterModify, HttpStatus.OK);
    }
}
