package com.patika.service;

import com.patika.dto.request.ConnectionDto;
import com.patika.entity.Connection;
import com.patika.exception.ConflictException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(ConnectionService.class);

    public void createConnection(ConnectionDto connectionDto) {
      Connection connectionInDb = connectionRepository.findByEmail(connectionDto.getEmail());

      if (connectionInDb != null){
          //throw new IllegalArgumentException("this email already exists");
          log.info("email already exists : {}",connectionDto.getEmail());
          throw new ConflictException(String.format(ErrorMessage.RESOURCE_ALREADY_EXISTS, connectionInDb.getId()));
      }
      Connection connection = new Connection();
      connection.setEmail(connectionDto.getEmail());
      connection.setMobileNumber(connectionDto.getMobileNumber());
      /*String test = null;
      test.length();*/
      connectionRepository.save(connection);
        log.info("connection saved : {} - {}",connectionDto.getEmail(),connection );

    }
}
