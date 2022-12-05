package ru.maxim5858mru.urfu.java.lessons.springboot.rest.dao;

import org.springframework.stereotype.Repository;
import ru.maxim5858mru.urfu.java.lessons.springboot.rest.enity.Student;

import java.util.List;

@Repository
public interface StudentDAO {
    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);
}
