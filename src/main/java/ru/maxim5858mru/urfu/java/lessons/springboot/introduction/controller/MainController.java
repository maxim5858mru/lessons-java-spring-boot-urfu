package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service.ModifyRequestService;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service.ModifyService;

@Slf4j
@RestController
public class MainController {
    private final ModifyService modifyService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    private Environment environment;

    @Autowired
//    public MainController(@Qualifier("ModifyUid") ModifyService modifyService,
//    public MainController(@Qualifier("ModifyErrorMessage") ModifyService modifyService,
    public MainController(@Qualifier("ModifySystemTime") ModifyService modifyService,
                          ModifyRequestService modifyRequestService) {
        this.modifyService = modifyService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request) {
        var port = Integer.parseInt(environment.getProperty("local.server.port"));

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

        if (port == 8080) modifyRequestService.modifyRequest(request);
        var responseAfterModify = modifyService.modify(response);
        if (port == 8080) log.warn("Исходящий response: " + String.valueOf(responseAfterModify));

        return new ResponseEntity<>(responseAfterModify, HttpStatus.OK);
    }
}