package com.patika.repository;


import com.patika.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{

    boolean existsByConnectionId(Long connectionId);

    Page<Student> findAllByDepartmentId(Long id, Pageable pageable);

}
