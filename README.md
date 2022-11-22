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

## Лабораторная работа №2

### Цель работы

Доработка REST API сервера.

### Задачи

1. Доработка сервиса
2. Добавить логирование в сервис
3. Добавить преобразование входящего сообщения

### Код

Файл `application.properties`:

```properties
logging.level.org.springframework.web=ERROR
logging.level.ru.maxim5858mru.urfu.java.lessons.springboot=DEBUG
```

Интерфейс `ModifyService`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;

public interface ModifyService {
    Response modify(Response response);
}
```

Класс `ModifyUid`:

```java
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
```

Класс `ModifySystemTime`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;

@Service
@RequiredArgsConstructor
@Qualifier("ModifySystemTime")
public class ModifySystemTime implements ModifyService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime("");
        return response;
    }
}
```

Класс `ModifyErrorMessage`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;

@Service
@RequiredArgsConstructor
@Qualifier("ModifyErrorMessage")
public class ModifyErrorMessage implements ModifyService {
    @Override
    public Response modify(Response response) {
        response.setErrorMessage("Что-то сломалось");
        return response;
    }
}
```

Класс `MainController`:

```java
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
//    public MainController(@Qualifier("ModifyUid") ModifyService modifyService) {
//    public MainController(@Qualifier("ModifySystemTime") ModifyService modifyService) {
    public MainController(@Qualifier("ModifyErrorMessage") ModifyService modifyService) {
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

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
```

### Снимки экрана

![Снимок экрана в Postman при работе ModifyUid](images/Screenshot%20LW2.1%20Postman%20ModifyUid.png)

![Снимок экрана в Jetbrains Intelli IDEA при работе ModifyUid](images/Screenshot%20LW2.2%20Jetbrains%20Intelli%20IDEA%20ModifyUid.png)

![Снимок экрана в Postman при работе ModifySystemTime](images/Screenshot%20LW2.3%20Postman%20ModifySystemTime.png)

![Снимок экрана в Jetbrains Intelli IDEA при работе ModifySystemTime](images/Screenshot%20LW2.4%20Jetbrains%20Intelli%20IDEA%20ModifySystemTime.png)

![Снимок экрана в Postman при работе ModifyErrorMessage](images/Screenshot%20LW2.5%20Postman%20ModifyErrorMessage.png)

![Снимок экрана в Jetbrains Intelli IDEA при работе ModifyErrorMessage](images/Screenshot%20LW2.6%20Jetbrains%20Intelli%20IDEA%20ModifyErrorMessage.png)

## Лабораторная работа №3

### Цель работы

Доработать простой REST сервис и настроить связь между двумя сервисами.

### Задачи

1. Создание нового сервиса, который будет принимать request от сервиса разработанного в прошлой лабораторной работе;
2. Доработка сервиса ранее разработанного для того, чтобы он мог пересылать полученный и модифицированный request.

### Создание конфигурации для второго сервера

Чтобы не выполнять копирование файлов, можно создать отдельную конфигурацию, с другим указанными основным портом.

![Снимок экрана в Jetbrains Intelli IDEA при настройке конфигурации второго экземпляра сервиса](images/Screenshot%20LW3.3%20Jetbrains%20Intelli%20IDEA%20Second%20Service%20Configuration.png)

### Код

Интерфейс `ModifyRequestService`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import ru.maxim5858mru.urfu.java.lessons.springboot.model.Request;

public interface ModifyRequestService {
    void modifyRequest(Request request);
}
```

Класс `ModifyRequestSystemTime`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Request;

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
```

Класс `MainController`:
```java
package ru.maxim5858mru.urfu.java.lessons.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.model.Response;
import ru.maxim5858mru.urfu.java.lessons.springboot.service.ModifyRequestService;
import ru.maxim5858mru.urfu.java.lessons.springboot.service.ModifyService;

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
```

### Снимки экрана

![Снимок экрана в Jetbrains Intelli IDEA](images/Screenshot%20LW3.1%20Jetbrains%20Intelli%20IDEA.png)

![Снимок экрана в Jetbrains Intelli IDEA окна Services с логами от второго сервиса](images/Screenshot%20LW3.2%20Jetbrains%20Intelli%20IDEA%20Second%20Service.png)