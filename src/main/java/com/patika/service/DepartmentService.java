package com.patika.service;

import com.patika.dto.request.DepartmentRequestDto;
import com.patika.entity.Department;
import com.patika.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void createDepartment(DepartmentRequestDto departmentDto){
        // uniq kontrolü
        if (departmentRepository.existsBydepartmentCode(departmentDto.getDepartmentCode())){
            throw new IllegalArgumentException("Department with code "+ departmentDto.getDepartmentCode()+" already exists");
        }

        //todo doldurulacak
        Department department = new Department();
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);


    }

    public DepartmentRequestDto getDepartment(Long id) {
       Department department = departmentRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("Department not found with id : "+ id)
        );

       DepartmentRequestDto dto = new DepartmentRequestDto();
       dto.setDepartmentCode(department.getDepartmentCode());
       dto.setDepartmentName(department.getDepartmentName());

       return  dto;
    }


    public void removeDepartment(Long id) {
        if (!departmentRepository.existsById(id)){
            throw new NoSuchElementException("Department not found with id : "+ id);
        }
        departmentRepository.deleteById(id);

    }
}
