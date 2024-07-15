package com.kokadem.controller;

import com.kokadem.exceptıon.ResourceNotFoundException;
import com.kokadem.model.Student;
import com.kokadem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//APIs
// http://localhost/api/v1
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent(){
        return  studentService.getAllStudent();
    }

    @GetMapping("/student/v1/{id}")
    public Student getOneStudentV1(@PathVariable Long id){
       return studentService.getOneStudentV1(id);
    }

    // Sadece entity'nin varlığını kontrol eden endpoint
    @GetMapping("/student/exists/{id}")
    public ResponseEntity<String> checkStudentExists(@PathVariable Long id){
        boolean exists = studentService.checkStudentExists(id);
        if(exists){
            return ResponseEntity.ok("Student exists");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student does not exist");
        }
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable Long id) throws ResourceNotFoundException {
        return studentService.getOneStudent(id);
    }

    @PostMapping("/student")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @DeleteMapping("/student/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable Long id) throws ResourceNotFoundException {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/studentV1/{id}")
    public Student updateStudentV1(@PathVariable Long id, @RequestBody Student newStudent) throws ResourceNotFoundException {
        return studentService.updateStudentV1(id, newStudent);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student newStudent) throws ResourceNotFoundException {
        return studentService.updateStudent(id, newStudent);
    }


}
