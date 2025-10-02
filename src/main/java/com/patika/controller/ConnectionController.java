package com.patika.controller;

import com.patika.dto.request.ConnectionDto;
import com.patika.dto.request.DepartmentRequestDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.service.ConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    public ResponseEntity<PatikaResponse> saveDepartment(@RequestBody ConnectionDto connectionDto){
        PatikaResponse response = new PatikaResponse();
        try {
            connectionService.createConnection(connectionDto);

            response.setMessage("Connection successfully saved");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
