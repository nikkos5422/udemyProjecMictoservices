package com.appsdeveloperblog.photoapp.api.users.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseModel implements Serializable {
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
