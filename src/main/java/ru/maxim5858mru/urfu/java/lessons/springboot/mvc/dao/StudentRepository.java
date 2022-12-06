package ru.maxim5858mru.urfu.java.lessons.springboot.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim5858mru.urfu.java.lessons.springboot.mvc.enity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
