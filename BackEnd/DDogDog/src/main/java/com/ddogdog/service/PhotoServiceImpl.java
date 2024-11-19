package com.ddogdog.service;

import com.ddogdog.model.dao.PhotoDao;
import com.ddogdog.model.dto.Photo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoDao photoDao;

    public PhotoServiceImpl(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public boolean createPhoto(Photo photo) {
        int result = photoDao.insertPhoto(photo);
        return result > 0;
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoDao.getAllPhotos();
    }

    @Override
    public List<Photo> getPhotosByUserId(String userId) {
        return photoDao.getPhotosByUserId(userId);
    }

    @Override
    public Photo getPhotoById(Long photoId) {
        return photoDao.getPhotoById(photoId);
    }

    @Override
    public boolean updatePhoto(Photo photo) {
        int result = photoDao.updatePhoto(photo);
        return result > 0;
    }

    @Override
    public boolean deletePhoto(Long photoId) {
        int result = photoDao.deletePhoto(photoId);
        return result > 0;
    }
}
