package com.patika.controller;

import com.patika.dto.request.StudentDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.exception.message.ResponseMessage;
import com.patika.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<PatikaResponse> saveStudent(@RequestBody StudentDto studentDto){
        PatikaResponse response = new PatikaResponse();

           studentService.saveStudent(studentDto);

            response.setMessage(ResponseMessage.ENTITY_SAVED);
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);


    }
    @PutMapping("/{id}")
    public ResponseEntity<PatikaResponse> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto){
        PatikaResponse response = new PatikaResponse();

            studentService.updateStudent(id, studentDto);

            response.setMessage(ResponseMessage.ENTITY_UPDATED);
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatikaResponse> deleteStudent(@PathVariable Long id){
        PatikaResponse response = new PatikaResponse();

        studentService.deleteStudent(id);

        response.setMessage(ResponseMessage.ENTITY_REMOVED);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
