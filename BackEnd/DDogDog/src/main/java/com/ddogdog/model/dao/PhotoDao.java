package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Photo;
import java.util.List;

public interface PhotoDao {
    int insertPhoto(Photo photo);

    List<Photo> getAllPhotos();

    List<Photo> getPhotosByUserId(String userId);

    Photo getPhotoById(Long photoId);

    int updatePhoto(Photo photo);

    int deletePhoto(Long photoId);
}
