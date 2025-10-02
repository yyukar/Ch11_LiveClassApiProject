package com.patika.service;

import com.patika.dto.request.DepartmentRequestDto;
import com.patika.dto.request.UpdateDepartmentDto;
import com.patika.entity.Department;
import com.patika.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public void updateDepartment(UpdateDepartmentDto updateDepartmentDto) {
      Department department =  departmentRepository.findById(updateDepartmentDto.getId()).orElseThrow(
                ()-> new NoSuchElementException("Department not found with id : "+ updateDepartmentDto.getId())
        );

      if (departmentRepository.existsBydepartmentCode(department.getDepartmentCode())  && department.getDepartmentCode().equals(updateDepartmentDto.getDepartmentCode())  ){
          throw  new IllegalArgumentException("Department code "+ updateDepartmentDto.getDepartmentCode() + " already exists");
      }

      department.setDepartmentName(updateDepartmentDto.getDepartmentName());
      department.setDepartmentCode(updateDepartmentDto.getDepartmentCode());
      department.setUpdatedAt(LocalDateTime.now());
      departmentRepository.save(department);


    }

    public List<DepartmentRequestDto> getAllDepartments() {

       List<Department> departmentList = departmentRepository.findAll();

        List<DepartmentRequestDto>dtoList = new ArrayList<>();
        if (!departmentList.isEmpty()){
            for (Department department : departmentList){
                DepartmentRequestDto dto = new DepartmentRequestDto();
                dto.setDepartmentName(department.getDepartmentName());
                dto.setDepartmentCode(department.getDepartmentCode());
                //dto.setCreateAt();
                dtoList.add(dto);
            }
        }
        return dtoList;


    }
}
