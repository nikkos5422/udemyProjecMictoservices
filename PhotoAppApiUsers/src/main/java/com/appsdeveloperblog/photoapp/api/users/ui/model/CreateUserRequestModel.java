package com.appsdeveloperblog.photoapp.api.users.ui.model;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CreateUserRequestModel {
    @NotNull(message = "firstName can't be empty")
    private String firstName;
    @NotNull(message = "lastName can't be empty")
    private String lastName;
    @NotNull(message = "pswd can't be empty")
    private String password;
    @NotNull(message = "email can't be empty")
    private String email;
}
