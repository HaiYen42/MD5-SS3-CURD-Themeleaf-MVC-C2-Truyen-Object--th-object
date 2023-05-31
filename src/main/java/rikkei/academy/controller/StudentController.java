package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.model.Student;
import rikkei.academy.service.IStudentService;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/student"})
public class StudentController {
    @Autowired
    private IStudentService studentService;
    @GetMapping
    public String showListStudent(Model model){
       List<Student> studentList=studentService.findAll();
       model.addAttribute("student", studentList);
        return "student/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model){
        Student student= studentService.findById(id);
        model.addAttribute("student", student);
        return "student/detail";
    }
    @GetMapping("/create")
    public String showFormCreate(Model model){
        Student student = new Student();
        model.addAttribute(student);
        return "student/create";
    }
    @PostMapping("/create")
    public String actionCreate(Student student){
        Long id = 0L;
        if (studentService.findAll().size() == 0) {
            id = 1L;
        } else {
            id = studentService.findAll().get(studentService.findAll().size() - 1).getId() + 1;
        }
        student.setId(id);
        studentService.save(student);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable("id") Long id,
                               Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "/student/edit";
    }
    @PostMapping("/edit/{id}")
    public String actionEdit(Student student){
        System.out.println(student);
        studentService.save(student);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String showFormDelete(@PathVariable("id") Long id,
                                 Model model){
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student/delete";
    }
    @PostMapping("/delete/{id}")
    public String actionDelete(Student student){
        System.out.println(student);
        studentService.deleteById(student.getId());
//        studentService.deleteById(id);
        return "redirect:/";
    }

}
