package com.gunnarro.imagemanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.repository.ImageManagerRepository;
import com.gunnarro.imagemanager.service.ImageService;

public class ImageServiceImpl implements ImageService {

    @Autowired
    @Qualifier("imagemanagerRepository")
    private ImageManagerRepository imagemanagerRepository;

    @Override
    public User getUser(String userName) {
        return imagemanagerRepository.getUser(userName);
    }

    @Override
    public ImageDetail getImage(Integer id) {
        return imagemanagerRepository.getImage(id);
    }

    @Override
    public List<ImageDetail> getImages(Integer userId) {
        return imagemanagerRepository.getImages(userId);
    }

    @Override
    public int saveImage(ImageDetail imageDetail) {
        if (imageDetail.isNew()) {
            return imagemanagerRepository.createImage(imageDetail);
        } else {
            return imagemanagerRepository.updateImage(imageDetail);
        }
    }

    @Override
    public boolean deleteImage(Integer imageId) {
        return imagemanagerRepository.deleteImage(imageId);
    }

}
