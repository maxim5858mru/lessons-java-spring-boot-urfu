-- Создание таблицы и добавление в неё данных

-- Удаление строй таблицы
DROP TABLE IF EXISTS Students ;

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