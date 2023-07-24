package com.appsdeveloperblog.photoapp.api.users.shared;

import com.appsdeveloperblog.photoapp.api.users.ui.model.AlbumResponseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
}
