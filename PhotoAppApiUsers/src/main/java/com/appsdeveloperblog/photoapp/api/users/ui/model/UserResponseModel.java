package com.appsdeveloperblog.photoapp.api.users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AlbumResponseModel> albums;
}
