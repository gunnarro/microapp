package com.gunnarro.dietmanager.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gunnarro.dietmanager.domain.log.ImageResource;
import com.gunnarro.dietmanager.service.FileUploadService;
import com.gunnarro.dietmanager.service.exception.UploadFileException;
import com.gunnarro.dietmanager.service.exception.UploadFileNotFoundException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	@Autowired
	private Environment environment;

	private Path rootLocation;

	@PostConstruct
	public void init() {
		try {
			this.rootLocation = Paths.get(environment.getProperty("fileupload.root.dir"));
			Files.createDirectories(rootLocation);
			LOG.debug("root dir: {}", rootLocation.toString());
			System.out.println(rootLocation.toString());
		} catch (Exception e) {
			LOG.error("Error init root dir: {}, error: {}", environment.getProperty("fileupload.root.dir"), e);
			throw new UploadFileException("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file, String id, String description) {
		try {
			if (file.isEmpty()) {
				throw new UploadFileException("Failed to store empty file!");
			}
			if (file.getOriginalFilename().contains("..")) {
				// This is a security check
				throw new UploadFileException(
						"Cannot store file with relative path outside current directory " + file.getOriginalFilename());
			}
			Path userDir = getUserImageDir(id);
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			LOG.debug("Store file: {}", userDir.resolve(filename).toString());
			Files.copy(file.getInputStream(), userDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			LOG.error(null, e);
			throw new UploadFileException("Failed to store file!", e);
		}
	}

	@Override
	public List<ImageResource> getImages(String id) {
		Path userDir = Paths.get(rootLocation.toString() + "/" + id);
		if (!Files.exists(userDir, new LinkOption[] {})) {
			return null;
		}
		try {
			return Files
					.walk(userDir, 1).filter(path -> !path.equals(userDir)).map(path -> new ImageResource(id,
							path.toFile().getName(), this.rootLocation.relativize(path).toString()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new UploadFileException("Failed to read stored files", e);
		}
	}

	@Override
	public void deleteImage(String id, String fileName) {
		try {
			String path = String.format("%s/%s", getUserImageDir(id).toString(), fileName);
			if (new File(path).delete()) {
				LOG.debug("deleted: {}", path);
			} else {
				LOG.error("error deleting {}", path);
			}
		} catch (IOException e) {
			LOG.error("", e);
		}
	}

	@Override
	public Stream<Path> loadAll(String id) {
		try {
			Path userImageDir = getUserImageDir(id);
			return Files.walk(userImageDir, 1).filter(path -> !path.equals(userImageDir))
					.map(path -> userImageDir.relativize(path));
		} catch (Exception e) {
			LOG.error("root path: {}", this.rootLocation.toAbsolutePath());
			throw new UploadFileException("Failed to read stored files", e);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	private Path getUserImageDir(String id) throws IOException {
		Path userDir = Paths.get(rootLocation.toString() + "/" + id);
		if (!Files.exists(userDir, new LinkOption[] {})) {
			Files.createDirectories(userDir);
			LOG.debug("created images dir: {}", userDir.toString());
		}
		return userDir;
	}

	@Override
	public Resource loadAsResource(String id, String filename) {
		try {
			Path file = getUserImageDir(id).resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new UploadFileNotFoundException("Could not read file: " + id + ", " + filename);

			}
		} catch (Exception e) {
			throw new UploadFileNotFoundException("Could not read file: " + id + ", " + filename, e);
		}
	}

	@Override
	public void deleteAll(String id) {
		try {
			FileSystemUtils.deleteRecursively(getUserImageDir(id).toFile());
		} catch (IOException e) {
			throw new UploadFileNotFoundException("Could not delete all files: " + id);
		}
	}

}
