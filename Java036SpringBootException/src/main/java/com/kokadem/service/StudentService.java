package com.kokadem.service;

import com.kokadem.exceptıon.ErrorMessage;
import com.kokadem.exceptıon.ResourceNotFoundException;
import com.kokadem.model.Student;
import com.kokadem.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        return  ResponseEntity.ok(students);
    }

    public Student getOneStudentV1(Long id) {
        try {
            // proxy nesnesine erişilmeyen durumlarda getReferenceById daha performanslı.
            // Belirtilen ID'ye sahip entity'nin proxy nesnesini döndürür
            Student student = studentRepository.getReferenceById(id);
            return initializeAndUnproxy(student);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format(ErrorMessage.RESOURCE_NOT_FOUND,id));
        }
    }
    public Student getStudentWithId(Long id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,id)));
        return student;
    }

    // Proxy nesnesini gerçek nesneye dönüştürmek için yardımcı metod
    private Student initializeAndUnproxy(Student student) {
        if(student instanceof HibernateProxy){
            Hibernate.initialize(student);
            student = (Student) ((HibernateProxy) student).getHibernateLazyInitializer().getImplementation();
        }
        return student;
    }

    public boolean checkStudentExists(Long id) {
        try{
            //FIXME check that the student exists
            studentRepository.getReferenceById(id).getId();
            return true; // Eğer proxy nesnesi dönerse, entity veritabanında var demektir
        }catch (EntityNotFoundException e) {
            return false; // Eğer entity bulunamazsa, false döner
        }
    }

    public ResponseEntity<Student> getOneStudent(Long id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,id)));
        return ResponseEntity.ok().body(student);
    }

    public Student createStudent(Student student) {
        if(studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, student.getEmail()));
        }
        return  studentRepository.save(student);
    }

    public Map<String, Boolean> deleteStudent(Long id) throws ResourceNotFoundException {
        Student student = getStudentWithId(id);
        studentRepository.deleteById(id);
        Map<String,Boolean> deleteResult = new HashMap<>();
        deleteResult.put("Deleted ID: "+id, Boolean.TRUE);
        return deleteResult;
    }
    //FIXME hata veriyor firstName shoud not null
    public Student updateStudentV1(Long id, Student newStudent) {
        Student student = studentRepository.findById(id).get();
        newStudent.setId(student.getId());
        return studentRepository.save(newStudent);
    }


    public ResponseEntity<Student> updateStudent(Long id, Student newStudent) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,id)));
        newStudent.setId(id);
        return ResponseEntity.ok(studentRepository.save(newStudent));
    }
}
