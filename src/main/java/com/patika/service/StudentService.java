package com.patika.service;

import com.patika.dto.request.StudentDto;
import com.patika.entity.Connection;
import com.patika.entity.Department;
import com.patika.entity.Student;
import com.patika.enums.Status;
import com.patika.exception.ConflictException;
import com.patika.exception.ResourceNotFoundException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ConnectionRepository;
import com.patika.repository.DepartmentRepository;
import com.patika.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ConnectionRepository connectionRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository, ConnectionRepository connectionRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.connectionRepository = connectionRepository;
        this.departmentRepository = departmentRepository;
    }

    public void saveStudent(StudentDto studentDto) {

        boolean isExistingStudent = studentRepository.existsByConnectionId(studentDto.getConnectionId());
        if (isExistingStudent){
            throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS, studentDto.getConnectionId()));
        }

        Student student = new Student();

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGender(studentDto.getGender());
        student.setStatus(Status.ACTIVE);

        Connection connection = connectionRepository.findById(studentDto.getConnectionId()).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,studentDto.getConnectionId() ))
        );
        student.setConnection(connection);

        Department department = departmentRepository.findById(studentDto.getDepartmentId()).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,studentDto.getDepartmentId() ))
        );
        student.setDepartment(department);

        studentRepository.save(student);


    }

    public void updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id ))
        );
        if (studentDto.getFirstName()!=null){
            student.setFirstName(studentDto.getFirstName());
        }
        if (studentDto.getLastName()!=null){
            student.setLastName(studentDto.getLastName());
        }
        if (studentDto.getGender()!=null){
            student.setGender(studentDto.getGender());
        }


        if (studentDto.getConnectionId()!=null){
            Connection connection = connectionRepository.findById(studentDto.getConnectionId()).orElseThrow(
                    ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,studentDto.getConnectionId() ))
            );
            student.setConnection(connection);
        }

        if (studentDto.getDepartmentId()!=null){
            Department department = departmentRepository.findById(studentDto.getDepartmentId()).orElseThrow(
                    ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND,studentDto.getDepartmentId() ))
            );
            student.setDepartment(department);
        }


        studentRepository.save(student);

    }

    public void deleteStudent(Long id) {
       boolean isExistingStudent = studentRepository.existsById(id);
        if (!isExistingStudent){
            throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id ));
        }
        studentRepository.deleteById(id);

       /* Student student = studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id ))
        );
        studentRepository.delete(student);*/

    }
}
