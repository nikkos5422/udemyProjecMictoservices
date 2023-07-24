package com.appsdeveloperblog.photoapp.api.users.service;

import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.data.UsersRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;
import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    private final Environment environment;


    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RestTemplate restTemplate, Environment environment) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        UserEntity user = mapper().map(userDto, UserEntity.class);
        user.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        usersRepository.save(user);
        return mapper().map(user, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity entity = usersRepository.findByEmail(email);
        if (entity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new ModelMapper().map(entity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);

        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });
        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

//        log.info("Before calling albums Microservice");
//        List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
//        log.info("After calling albums Microservice");

        userDto.setAlbums(albumsList);

        return userDto;
    }

    private static ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        return modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = usersRepository.findByEmail(username);
        if (entity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
