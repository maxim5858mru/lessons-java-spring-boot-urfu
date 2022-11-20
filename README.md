# Отчёты по лабораторным работы Java/Spring Boot

***УрФУ ИРИТ-РФТ  
В рамках изучения дисциплины  
«Системная инженерия»  
Студента группы РИЗ-300016у  
Кулакова Максима Ивановича***

***Преподаватель Архипов Николай Александрович***

## Лабораторная работа №1

### Цель работы

Разработать простой REST сервис.

### Описание входящих сообщений

| №   | Наименование ключа | Тип данных | Обязательность     | Описание                              |
|-----|--------------------|------------|--------------------|---------------------------------------|
| 1   | `uid`              | `String`   | Да, до 32 символов | Уникальный идентификатор сообщение    |
| 2   | `operationUid`     | `String`   | Да, до 32 символов | Уникальный идентификатор операции     |
| 3   | `systemName`       | `String`   | Нет                | Имя системы отправителя               |
| 4   | `systemTime`       | `String`   | Да                 | Время создания сообщения              |
| 5   | `source`           | `String`   | Нет                | Наименование ресурса                  |
| 6   | `communicationId`  | `int`      | Нет                | Уникальный идентификатор коммуникации |
| 7   | `templateId`       | `int`      | Нет                | Уникальный идентификатор шаблона      |
| 8   | `productCode`      | `int`      | Нет                | Код продукта                          |
| 9   | `smsCode`          | `int`      | Нет                | SMS код                               |

Пример входящего сообщения:

```json
{
  "uid": "1",
  "operationUid": "",
  "systemName": "",
  "systemTime": "",
  "source": "",
  "communicationId": 100,
  "templateId": 8,
  "productCode": 1500,
  "smsCode": 10
}
```

### Описание исходящих сообщений

| №   | Наименование ключа | Тип данных | Обязательность | Описание                           | Возможные значения                                           |
|-----|--------------------|------------|----------------|------------------------------------|--------------------------------------------------------------|
| 1   | `uid`              | `String`   | Да             | Уникальный идентификатор сообщение |                                                              |
| 2   | `operationUid`     | `String`   | Да             | Уникальный идентификатор операции  |                                                              |
| 3   | `systemName`       | `String`   | Нет            | Имя системы отправителя            | `ERP`                                                        |
| 4   | `code`             | `String`   | Да             | Успешность операции                | `success` или `failed`                                       |
| 5   | `errorCode`        | `String`   | Да             | Класс ошибки                       | `ProcessException`, `ValidationException`, `UnknowException` |
| 6   | `errorMessage`     | `String`   | Да             | Сообщение об ошибке                | "Ошибка процесса", "Ошибка валидации", "Неизвестная ошибка"  |

Пример исходящего сообщения:

```json
{
  "uid": "1",
  "operationUid": "",
  "systemTime": "",
  "code": "failed",
  "errorCode": "ValidationException",
  "errorMessage": "Ошибка валидации"
}
```

### Код

Класс `Request`:

```java
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
```

Класс `Response`:

```java
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
     * Имя системы отправителя
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
```

Класс `MainController`:

```java
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
```

### Снимки экрана

![Снимок экрана в Postman](images/Screenshot%20LW1.1%20Postman.png)

![Снимок экрана в Jetbrains Intelli IDEA](images/Screenshot%20LW1.2%20Jetbrains%20Intelli%20IDEA.png)