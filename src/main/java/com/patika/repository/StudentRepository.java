package com.patika.repository;


import com.patika.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long>{

    boolean existsByConnectionId(Long connectionId);

    Page<Student> findAllByDepartmentId(Long id, Pageable pageable);


    @Query(value = """
                select count(distinct s.id)
                from student s join  image_file i
                on s.id = i.student_id where i.id = :imageId
            """, nativeQuery = true)
    Integer findStudentCountByImageId(@Param("imageId") String imageId);

}
