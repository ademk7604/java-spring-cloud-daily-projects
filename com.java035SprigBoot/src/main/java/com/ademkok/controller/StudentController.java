package com.ademkok.controller;

import com.ademkok.model.Student;
import com.ademkok.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//   http://localhost:8090/api/v1
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    // IoC - Kontrolü Spring'e ver.
    // DI - Bağımlılıkların enjeksiyonu.


    // Servis field olarak enjekte ediliyor. Eski
    /*
    @Autowired
    StudentService studentService;
    */

    // Servis enjekte ediliyor. Yeni
    // the StudentController has a dependency on a StudentService
    private final StudentService studentService;

    /*    a constructor so that the Spring container can inject a StudentService
    public StudentController(StudentService studentService) {
        this.studentService=studentService;
    }

     */

    //   http://127.0.0.1:8090/api/v1/hello
    //   http://localhost:8090/api/v1/hello

    @RequestMapping(value = "/hello", method=RequestMethod.GET)
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot - Student Controller";
    }


    // API
    // GET  - SELECT ALL
    //   http://localhost:8090/api/v1/students              // v1
    //   http://localhost:8090/api/v1/student/all           // v2 ikili farkli aralarda{} kullaniyoruz
    @GetMapping({"/students", "/student/all"})
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    // GET  - SELECT WHERE
    //   http://localhost:8090/api/v1/student/1 //(name = "id") zorunlu degil
    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable (name = "id") Long id){
        return studentService.getStudent(id);
    }

    // POST  - INSERT
    //   http://localhost:8090/api/v1/student
    @PostMapping("/student")
    public  Student addStudent(@RequestBody Student student){
       return studentService.addStudent(student);
    }

    // PUT - UPDATE
    //   http://localhost:8090/api/v1/student/1
    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student newStudent){
        return studentService.updateStudent(id, newStudent);
    }

    @DeleteMapping ("/student/{id}")
    public String deleteStudent(@PathVariable Long id){
      return studentService.deleteStudent(id);
    }
}
