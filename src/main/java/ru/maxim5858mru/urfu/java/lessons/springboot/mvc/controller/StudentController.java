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
