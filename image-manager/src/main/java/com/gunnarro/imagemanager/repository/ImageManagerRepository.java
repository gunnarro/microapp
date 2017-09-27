package com.gunnarro.imagemanager.repository;

import java.util.List;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.party.User;

public interface ImageManagerRepository {

	public User getUser(String userName);
	
	public User getUser(Integer userId);

	public ImageDetail getImage(Integer id);

	public List<ImageDetail> getImages(Integer userId);

	public boolean deleteImage(Integer imageId);

	public int createImage(ImageDetail imageDetail);

	public int updateImage(ImageDetail imageDetail);


}
