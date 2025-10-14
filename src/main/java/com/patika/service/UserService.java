package com.patika.service;

import com.patika.dto.request.LoginRequest;
import com.patika.dto.request.RegisterRequest;
import com.patika.dto.response.LoginResponse;
import com.patika.entity.Role;
import com.patika.entity.User;
import com.patika.enums.RoleType;
import com.patika.exception.ConflictException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.UserRepository;
import com.patika.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException(
                        String.format(ErrorMessage.RESOURCE_NOT_FOUND, email)));
        return user;

    }

    public void saveUser(RegisterRequest registerRequest) {
        //bu email ile daha önce kayoıt yapılmış mı??
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS ,registerRequest.getEmail()));
        }

        //role
        Role role = roleService.findByType(RoleType.ROLE_EMPLOYEE);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //şifre
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        //yeni oluşturma
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        userRepository.save(user);

    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken upAt = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(upAt);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }
}
