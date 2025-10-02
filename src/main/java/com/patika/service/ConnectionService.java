package com.patika.service;

import com.patika.dto.request.ConnectionDto;
import com.patika.entity.Connection;
import com.patika.exception.ConflictException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ConnectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public void createConnection(ConnectionDto connectionDto) {
      Connection connectionInDb = connectionRepository.findByEmail(connectionDto.getEmail());

      if (connectionInDb != null){
          //throw new IllegalArgumentException("this email already exists");
          throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS, connectionInDb.getId()));
      }
      Connection connection = new Connection();
      connection.setEmail(connectionDto.getEmail());
      connection.setMobileNumber(connectionDto.getMobileNumber());
      String test = null;
      test.length();
      connectionRepository.save(connection);

    }
}
