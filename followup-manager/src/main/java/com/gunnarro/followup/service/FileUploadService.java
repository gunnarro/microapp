package com.gunnarro.followup.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.gunnarro.followup.domain.log.ImageResource;

public interface FileUploadService {

    public Stream<Path> loadAll(String id);

    public Resource loadAsResource(String id, String filename);

    public void deleteAll(String id);

    public void store(MultipartFile file, String id, String description);

    public List<ImageResource> getImages(String id);

	public void deleteImage(String id, String path);
}
