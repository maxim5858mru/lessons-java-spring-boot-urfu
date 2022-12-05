package ru.maxim5858mru.urfu.java.lessons.springboot.rest.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
        var query = entityManager.createQuery("FROM Student");
        List<Student> allStudents = query.getResultList();
        log.info("Get all students: " + allStudents);
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
        var query = entityManager.createQuery("DELETE FROM Student WHERE id = :studentId");
        query.setParameter("studentId", id);
        query.executeUpdate();
    }
}
