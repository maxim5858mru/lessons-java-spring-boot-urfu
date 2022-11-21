package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;

@Service
@RequiredArgsConstructor
@Qualifier("ModifyUid")
public class ModifyUid implements ModifyService {
    public Response modify(Response response) {
        response.setUid("New UID");
        return response;
    }
}
