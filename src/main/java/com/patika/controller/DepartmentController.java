package com.patika.controller;

import com.patika.dto.request.DepartmentRequestDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.entity.Department;
import com.patika.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//http://localhost:8080/department/save
//http://localhost:8080/department/getAllDeps
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping("/save")
    public ResponseEntity<PatikaResponse> saveDepartment(@RequestBody DepartmentRequestDto department){
        PatikaResponse response = new PatikaResponse();
           try {
               departmentService.createDepartment(department);

               response.setMessage("Department successfully saved");
               response.setSuccess(true);
               return new ResponseEntity<>(response, HttpStatus.CREATED);
           }catch (IllegalArgumentException e){
               response.setMessage(e.getMessage());
               response.setSuccess(false);
               return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           }



    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentRequestDto> getDepartmentById(@PathVariable Long id){
          DepartmentRequestDto dto =  departmentService.getDepartment(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PatikaResponse> removeDepartmentById(@PathVariable Long id){
        PatikaResponse response = new PatikaResponse();
        try {

            departmentService.removeDepartment(id);

            response.setMessage("Department successfully deleted");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

}
