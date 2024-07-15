package com.kokadem.repository;

import com.kokadem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Spring i yormamak icin repository yazmak lazim
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Object> findByEmail(String email);
}
