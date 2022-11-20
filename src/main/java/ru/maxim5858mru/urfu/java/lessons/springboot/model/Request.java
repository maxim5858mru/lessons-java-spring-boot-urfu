package ru.maxim5858mru.urfu.java.lessons.springboot.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class Request {
    /**
     * Уникальный идентификатор сообщение
     */
    @NotBlank
    @Size(max = 32)
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    @NotBlank
    @Size(max = 32)
    private String operationUid;

    /**
     * Имя системы отправителя
     */
    @NotBlank
    private String systemName;

    /**
     * Время создания сообщения
     */
    private String systemTime;

    /**
     * Наименование ресурса
     */
    @NotBlank
    private String source;

    /**
     * Уникальный идентификатор коммуникации
     */
    private Integer communicationId;

    /**
     * Уникальный идентификатор шаблона
     */
    private Integer templateId;

    /**
     * Код продукта
     */
    @Max(7)
    private Integer productCode;

    /**
     * SMS код
     */
    @Max(5)
    private Integer smsCode;
}
