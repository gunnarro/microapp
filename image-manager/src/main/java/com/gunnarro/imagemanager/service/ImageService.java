package com.gunnarro.imagemanager.service;

import java.util.List;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.party.User;

public interface ImageService {

    public User getUser(String username);

    public List<ImageDetail> getImages(Integer userId);

    public ImageDetail getImage(Integer id);

	public int saveImage(ImageDetail imageDetail);

	public boolean deleteImage(Integer imageId);


}
