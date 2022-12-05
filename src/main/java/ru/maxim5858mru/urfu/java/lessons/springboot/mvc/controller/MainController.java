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
