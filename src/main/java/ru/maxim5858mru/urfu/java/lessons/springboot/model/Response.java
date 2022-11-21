package ru.maxim5858mru.urfu.java.lessons.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    /**
     * Уникальный идентификатор сообщение
     */
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;

    /**
     * Время отправки сообщения
     */
    private String systemTime;

    /**
     * Успешность операции
     */
    private String code;

    /**
     * Класс ошибки
     */
    private String errorCode;

    /**
     * Сообщение об ошибке
     */
    private String errorMessage;
}
