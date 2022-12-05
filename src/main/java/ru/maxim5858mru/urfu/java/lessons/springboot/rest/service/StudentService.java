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
