package com.gunnarro.dietmanager.mvc.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gunnarro.dietmanager.service.FileUploadService;

/**
 * 
 * @author mentos
 *
 */
@Controller
public class FileUploadController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	public FileUploadController(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	private final FileUploadService fileUploadService;

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/upload/{id}")
	@ResponseBody
	public ModelAndView uploadedFileForm(@PathVariable String id, @RequestParam("redirectUri") String redirectUri) throws IOException {
		List<String> files = fileUploadService
				.loadAll(id).map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"getImageAsResource", id, path.getFileName().toString()).build().toString())
				.collect(Collectors.toList());
		ModelAndView modelView = new ModelAndView("upload/upload-file");
		modelView.getModel().put("id", id);
		modelView.getModel().put("files", files);
		modelView.getModel().put("redirectUri", redirectUri);
		return modelView;
	}

	
	/**
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description, @RequestParam("id") String id, @RequestParam("redirectUri") String redirectUri, RedirectAttributes redirectAttributes) {
		if (file == null) {
			// return error
			return "redirect:/upload/" + id;
		}
		fileUploadService.store(file, id, description);
		LOG.debug("Successfully uploaded: {}/{}", id, file.getName());
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
		if (redirectUri != null && !redirectUri.isEmpty()) {
			return String.format("redirect:%s", redirectUri);
		} else {
			return String.format("redirect:/upload/%s", id);
		}
	}

	
	/**
	 * 
	 * @param id
	 * @param filename
	 * @return
	 */
	@GetMapping(value = "/upload/files/{id}/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getImageAsResource(@PathVariable String id, @PathVariable String filename) {
		Resource resource = fileUploadService.loadAsResource(id, filename);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @param fileName
	 * @return
	 */
	@DeleteMapping("/upload/files/delete")
	public String deleteImage(@RequestParam("id") String id, @RequestParam("filename") String fileName) {
		fileUploadService.deleteImage(id, fileName);
		return "redirect:/upload/" + id;
	}

}
