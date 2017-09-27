package com.gunnarro.sportsteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.repository.SportsTeamRepository;

public interface ImageService {

	public List<ImageDetail> getImages(Integer userId);

	public int saveImage(Integer userId, ImageDetail imageDetail);

	public boolean deleteImage(Integer imageId);

}
