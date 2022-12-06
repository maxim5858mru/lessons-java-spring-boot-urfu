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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model;

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model;

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;

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

![Снимок экрана в Jetbrains IntelliJ IDEA](images/Screenshot%20LW1.2%20Jetbrains%20IntelliJ%20IDEA.png)

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;

public interface ModifyService {
    Response modify(Response response);
}
```

Класс `ModifyUid`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;

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
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Request;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Response;
import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service.ModifyService;

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

![Снимок экрана в Jetbrains IntelliJ IDEA при работе ModifyUid](images/Screenshot%20LW2.2%20Jetbrains%20IntelliJ%20IDEA%20ModifyUid.png)

![Снимок экрана в Postman при работе ModifySystemTime](images/Screenshot%20LW2.3%20Postman%20ModifySystemTime.png)

![Снимок экрана в Jetbrains IntelliJ IDEA при работе ModifySystemTime](images/Screenshot%20LW2.4%20Jetbrains%20IntelliJ%20IDEA%20ModifySystemTime.png)

![Снимок экрана в Postman при работе ModifyErrorMessage](images/Screenshot%20LW2.5%20Postman%20ModifyErrorMessage.png)

![Снимок экрана в Jetbrains IntelliJ IDEA при работе ModifyErrorMessage](images/Screenshot%20LW2.6%20Jetbrains%20IntelliJ%20IDEA%20ModifyErrorMessage.png)

## Лабораторная работа №3

### Цель работы

Доработать простой REST сервис и настроить связь между двумя сервисами.

### Задачи

1. Создание нового сервиса, который будет принимать request от сервиса разработанного в прошлой лабораторной работе;
2. Доработка сервиса ранее разработанного для того, чтобы он мог пересылать полученный и модифицированный request.

### Создание конфигурации для второго сервера

Чтобы не выполнять копирование файлов, можно создать отдельную конфигурацию, с другим указанными основным портом.

![Снимок экрана в Jetbrains IntelliJ IDEA при настройке конфигурации второго экземпляра сервиса](images/Screenshot%20LW3.3%20Jetbrains%20IntelliJ%20IDEA%20Second%20Service%20Configuration.png)

### Код

Интерфейс `ModifyRequestService`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.introduction.service;

import ru.maxim5858mru.urfu.java.lessons.springboot.introduction.model.Request;

public interface ModifyRequestService {
    void modifyRequest(Request request);
}
```

Класс `ModifyRequestSystemTime`:

```java
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
```

Класс `MainController`:

```java
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
```

### Снимки экрана

![Снимок экрана в Jetbrains IntelliJ IDEA](images/Screenshot%20LW3.1%20Jetbrains%20IntelliJ%20IDEA.png)

![Снимок экрана в Jetbrains IntelliJ IDEA окна Services с логами от второго сервиса](images/Screenshot%20LW3.2%20Jetbrains%20IntelliJ%20IDEA%20Second%20Service.png)

## Лабораторная работа №4

### Цель работы

Разработать REST Full Service.

### Настройка базы данных H2

![Снимок экрана в Jetbrains IntelliJ IDEA с созданием базы данных H2](images/Screenshot%20LW4.1%20Jetbrains%20IntelliJ%20IDEA%20Create%20database.png)

Скрипт для создания таблицы и добавления данных:

```h2
-- Создание таблицы и добавление в неё данных

-- Удаление строй таблицы
DROP TABLE IF EXISTS Students;

-- Создание таблицы
CREATE TABLE Students
(
    Id      INT NOT NULL AUTO_INCREMENT,
    Name    NVARCHAR(15),
    Surname NVARCHAR(25),
    Faculty NVARCHAR(20),
    Age     INT,
    CONSTRAINT PK_Students PRIMARY KEY (Id)
);

-- Добавление данных
INSERT INTO Students (Name, Surname, Faculty, Age)
VALUES (N'Иван', N'Иванов', 'IT', 28),
       (N'Пётр', N'Петрова', 'Math', 25),
       (N'Марина', N'Марьина', 'IT', 22);

-- Вывод данных
SELECT *
FROM Students;
```

![Снимок экрана в Jetbrains IntelliJ IDEA с выводом данных из таблицы Students](images/Screenshot%20LW4.2%20Jetbrains%20IntelliJ%20IDEA%20Select%20all%20records%20from%20table%20Students.png)

### Настройка JetBrains IntelliJ IDEA

Файл `application.properties`:

```properties
logging.level.org.springframework.web=ERROR
logging.level.ru.maxim5858mru.urfu.java.lessons.springboot.introduction=DEBUG
logging.level.ru.maxim5858mru.urfu.java.lessons.springboot.rest=DEBUG
spring.jpa.open-in-view=false
spring.datasource.url=jdbc:h2:./database/students
```

Файл `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>demo</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <version>2.7.4</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

![Снимок экрана в Jetbrains IntelliJ IDEA с окном настроек Compiler](images/Screenshot%20LW4.3%20Jetbrains%20IntelliJ%20IDEA%20Settings%20of%20Compiler.png)

![Снимок экрана в Jetbrains IntelliJ IDEA с окном настроек Advanced Settings](images/Screenshot%20LW4.4%20Jetbrains%20IntelliJ%20IDEA%20Advanced%20Settings.png)

### Разработка REST сервиса

| HTTP метод | URL              | CRUD операция             |
|------------|------------------|---------------------------|
| `GET`      | api/student      | Получение всех студентов  |
| `GET`      | api/student/{id} | Получение одного студента |
| `POST`     | api/student      | Добавление студента       |
| `PUT`      | api/student      | Изменение студента        |
| `DELETE`   | api/student/{id} | Удаление студента         |

Интерфейс `StudentDAO`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.rest.dao;

import org.springframework.stereotype.Repository;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;

import java.util.List;

@Repository
public interface StudentDAO {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);
}
```

Класс `StudentDAOImplentation`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.rest.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class StudentDAOImplementation implements StudentDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudents() {
        var query = entityManager.createQuery("FROM STUDENTS");
        List<Student> allStudents = query.getResultList();
        log.info("Get all student: " + allStudents);
        return allStudents;
    }

    @Override
    public Student saveStudent(Student student) {
        return entityManager.merge(student);
    }

    @Override
    public Student getStudent(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public void deleteStudent(int id) {
        var query = entityManager.createQuery("DELETE STUDENTS WHERE ID = :studentId");
        query.setParameter("studentId", id);
        query.executeUpdate();
    }
}

```

Интерфейс `StudentService`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.rest.service;

import org.springframework.stereotype.Service;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);
}
```

Класс `StudentServiceImplentation`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.dao.StudentDAO;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;

import java.util.List;

@Service
public class StudentServiceImplementation implements StudentService {
    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }
}
```

Класс `MainController`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public List<Student> showAllStudents() {
        List<Student> allStudents = studentService.getAllStudents();
        return allStudents;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable("id") int id) {
        return studentService.getStudent(id);
    }

    @PostMapping("/student")
    public Student saveStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @PutMapping("/student")
    public Student updateStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable("id") int id) {
        studentService.deleteStudent(id);
    }
}
```

### Запуск и тестирование приложения

![Снимок экрана в Postman с тестированием запроса Get All](images/Screenshot%20LW4.5%20Postman%20Get%20All.png)

![Снимок экрана в Postman с тестированием запроса Get One](images/Screenshot%20LW4.6%20Postman%20Get%20One.png)

![Снимок экрана в Postman с тестированием запроса Add](images/Screenshot%20LW4.7%20Postman%20Add.png)

![Снимок экрана в Postman с тестированием запроса Update](images/Screenshot%20LW4.8%20Postman%20Update.png)

![Снимок экрана в Postman с тестированием запроса Delete](images/Screenshot%20LW4.9%20Postman%20Delete.png)

## Лабораторная работа №5

### Цель работы

Разработать простое приложение в веб-формой.

### Создание конфигурации для MVC

![Снимок экрана в Jetbrains IntelliJ IDEA при настройке конфигурации MVC сервиса](images/Screenshot%20LW5.3%20Jetbrains%20IntelliJ%20IDEA%20MVC%20Service%20Configuration.png)

### Код

Код класса `LessonsJavaSpringBootUrFUApplicationMVC`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LessonsJavaSpringBootUrFUApplicationMVC {
    public static void main(String[] args) {
        SpringApplication.run(LessonsJavaSpringBootUrFUApplicationMVC.class, args);
    }
}
```

Код класса `MainController`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/hello")   // Привязывает запросы GET /hello к методу showFirstPage()
    public String showFirstPage(@RequestParam(name = "name", required = false, defaultValue = "World")
                                String name,
                                Model model) {
        // Параметр строки запроса name привязан к параметру метода showFirstPage(), если он не указан, то используется
        // значение по умолчанию "World" (которое после добавляется в модель)
        model.addAttribute("name", name);
        return "first-page";
    }
}
```

Шаблон страницы `first-page.html`:

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<p th:text="'Hello, ' + ${name} + '!'"/>
</body>
</html>
```

### Тестирование приложения

![Снимок экрана в браузере с тестированием приложения](images/Screenshot%20LW5.1%20Jetbrains%20IntelliJ%20IDEA%20and%20Web-browser.png)

![Снимок экрана в браузере с тестированием приложения при передаче параметра](images/Screenshot%20LW5.2%20Jetbrains%20IntelliJ%20IDEA%20and%20Web-browser.png)

## Лабораторная работа №6

### Цель работы

Разработать веб-приложение с двумя веб-формами и базой данных.

### Настройка базы данных

Настройка базы данных для лабораторной работы №6 аналогична настройке выполненной в рамках лабораторной работы №4.

### Код

Класс `Student`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.mvc.enity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "Faculty")
    private String faculty;

    @Column(name = "Age")
    private int age;

    public Student() {
    }

    public Student(String name, String surname, String faculty, int age) {
        this.name = name;
        this.surname = surname;
        this.faculty = faculty;
        this.age = age;
    }
}
```

Класс `StudentRepository`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim5858mru.urfu.java.lessons.springboot.mvc.enity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
```

Класс `StudentController`:

```java
package ru.maxim5858mru.urfu.java.lessons.springboot.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.maxim5858mru.urfu.java.lessons.springboot.mvc.dao.StudentRepository;
import ru.maxim5858mru.urfu.java.lessons.springboot.mvc.enity.Student;

@Slf4j
@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/list")
    public ModelAndView getAllStudents() {
        log.info("/list -> connection");

        var mav = new ModelAndView("list-students");
        mav.addObject("students", studentRepository.findAll());
        return mav;
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        log.info("/addStudentForm -> connection");

        var mav = new ModelAndView("add-student-form");
        var student = new Student();
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/saveStudent")
    public String saveStudent(Student student) {
        log.info("/saveStudent -> connection");

        studentRepository.save(student);
        return "redirect:/list";
    }

    @GetMapping("showUpdateForm")
    public ModelAndView showUpdateForm(long id) {
        log.info("/showUpdateForm -> connection");

        var mav = new ModelAndView("add-student-form");
        var optionalStudent = studentRepository.findById(id);
        var student = new Student();

        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
        }

        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        return "redirect:/list";
    }
}
```

Шаблон страницы `list-students.html`:

```html

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>List Students</title>

    <link rel="stylesheet"
          type="text/css"
          href="https://cdn.datatables.net/v/bs4/dt-1.10.25/datatables.min.css"/>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
          crossorigin="anonymous"/>
</head>

<body>

<div class="container">

    <h3>List Students</h3>

    <hr/>
    <a th:href="@{/addStudentForm}" class="btn btn-primary">Add Student</a>
    <br/><br/>
    <table class="table table-bordered table-striped" id="studentTable">

        <thead>
        <tr>
            <th>Name</th>
            <th>Surname</th>
            <th>Faculty</th>
            <th>Age</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="student: ${students}">
            <td th:text="${student.surname}"/>
            <td th:text="${student.name}"/>
            <td th:text="${student.faculty}"/>
            <td th:text="${student.age}"/>
            <td>
                <a th:href="@{/showUpdateForm(studentId=${student.id})}" class="btn btn-info">Update</a>

                <a th:href="@{/deleteStudent(studentId=${student.id})}" class="btn btn-danger ml-2">Delete</a>
            </td>
        </tr>
        </tbody>

    </table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.25/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $("#studentTable").DataTable({
            'aoColumnDefs': [{
                'bSortable': false,
                'aTargets': [-1] /* 1st one, start by the right */
            }]
        });
    })
</script>
</body>
</html>
```

Шаблон страницы `add-student-form.html`:

```html

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Add Student</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">
</head>
<body>

<div class="container">

    <h3>Add New Student</h3>
    <hr/>

    <form th:action="@{/saveStudent}" th:object="${student}" method="POST">

        <input type="text" th:field="*{name}" class="form-control col-4 mb-4" placeholder="Enter Name"/>

        <input type="text" th:field="*{surname}" class="form-control col-4 mb-4" placeholder="Enter Surname"/>

        <input type="text" th:field="*{faculty}" class="form-control col-4 mb-4" placeholder="Enter Faculty"/>

        <input type="text" th:field="*{age}" class="form-control col-4 mb-4" placeholder="Enter Age"/>

        <button type="submit" class="btn btn-primary col-2">Save</button>

        <input type="hidden" th:field="*{id}"/>

    </form>
    <hr/>
    <a th:href="@{/list}">Back to list</a>
</div>
</body>
</html>
```

### Тестирование

![Снимок экрана в браузере с формой для добавления студента](images/Screenshot%20LW6.1%20Jetbrains%20IntelliJ%20IDEA%20and%20Add%20Student.png)

![Снимок экрана в браузере с попыткой сохранения изменений](images/Screenshot%20LW6.2%20Jetbrains%20IntelliJ%20IDEA%20and%20Add%20Student.png)

![Снимок экрана в браузере с выводом списка студентов](images/Screenshot%20LW6.3%20Jetbrains%20IntelliJ%20IDEA%20and%20List%20Students.png)
