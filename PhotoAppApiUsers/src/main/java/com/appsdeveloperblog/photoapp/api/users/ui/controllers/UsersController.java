package com.appsdeveloperblog.photoapp.api.users.ui.controllers;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.ui.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment environment;

    @Autowired
    private UsersService usersService;

    @GetMapping("/status")
    public String status() {
        return "working" + environment.getProperty("local.server.port")+", with token "+environment.getProperty("token.uber");
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel request) {
        UserDto userDto = mapper().map(request, UserDto.class);
UserDto createdUser = usersService.createUser(userDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(mapper().map(createdUser, CreateUserResponseModel.class));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {

        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }


    private static ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        return modelMapper;
    }
}
