package com.gunnarro.dietmanager.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.service.impl.FileUploadServiceImpl;

@ContextConfiguration(classes = { FileUploadServiceImpl.class })
@Ignore
public class FileUploadServiceImplTest extends DefaultTestConfig {

	@Autowired
	private FileUploadServiceImpl fileUploadServiceImpl;

	@Test
	public void getImages() {
		Assert.assertNotNull(fileUploadServiceImpl.getImages("123"));
	}
	
	@Test
	public void loadAll() {
		Assert.assertNotNull(fileUploadServiceImpl.loadAll("123"));
	}
}
