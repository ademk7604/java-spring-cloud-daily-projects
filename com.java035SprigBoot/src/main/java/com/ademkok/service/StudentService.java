package com.ademkok.service;

import com.ademkok.exception.DuplicateResourceException;
import com.ademkok.exception.ResourceNotFoundException;
import com.ademkok.exception.message.ErrorMessage;
import com.ademkok.model.Student;
import com.ademkok.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(Long id) {
       Student Student = studentRepository.findById(id).orElseThrow(
               ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,id)));
        return Student;
    }
    public Student addStudent(Student student) {
        Optional<Student> existingStudent= studentRepository.findByEmail(student.getEmail());
        if(existingStudent.isPresent()) {
            throw  new DuplicateResourceException(String.format(ErrorMessage.EMAIL_ALREADY_EXISTS,student.getEmail()));
        }
        return studentRepository.save(student);
    }


    public ResponseEntity<Student> updateStudent(Long id, Student newStudent) {
        Student student = getStudent(id);
        newStudent.setId(student.getId());
        studentRepository.save(newStudent);
        return ResponseEntity.ok(newStudent);
    }

    public String deleteStudent(Long id) {
        if(studentRepository.existsById(id)){
            Student student = studentRepository.getReferenceById(id);
            studentRepository.deleteById(id);
            return student.getFirstName() + " " + student.getLastName() + ": Basarili olarak silindi.";
        }else{
            return  String.format(ErrorMessage.EMAIL_ALREADY_NOT_EXISTS,id);
        }
    }
}
