package com.gunnarro.sportsteam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.repository.SportsTeamRepository;
import com.gunnarro.sportsteam.service.ImageService;

public class ImageServiceImpl implements ImageService {

	@Autowired
	@Qualifier("sportsTeamRepository")
	private SportsTeamRepository sportsTeamRepository;

	@Override
	public List<ImageDetail> getImages(Integer userId) {
		return sportsTeamRepository.getImages(userId);
	}

	@Override
	public int saveImage(Integer userId, ImageDetail imageDetail) {
		return sportsTeamRepository.createImage(imageDetail);
	}

	@Override
	public boolean deleteImage(Integer imageId) {
		return sportsTeamRepository.deleteImage(imageId);
	}
}
