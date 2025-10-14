package com.patika.service;

import com.patika.entity.Role;
import com.patika.enums.RoleType;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    public Role findByType(RoleType roleType) {
        Role role =  roleRepository.findByType(roleType).orElseThrow(()->
                new RuntimeException(String.format(
                        ErrorMessage.RESOURCE_NOT_FOUND, roleType.name())));
        return role;
    }
}
