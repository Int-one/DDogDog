package com.ddogdog.service;

import com.ddogdog.model.dto.Photo;
import java.util.List;

public interface PhotoService {
    boolean createPhoto(Photo photo);

    List<Photo> getAllPhotos();

    List<Photo> getPhotosByUserId(String userId);

    Photo getPhotoById(Long photoId);

    boolean updatePhoto(Photo photo);

    boolean deletePhoto(Long photoId);
}
