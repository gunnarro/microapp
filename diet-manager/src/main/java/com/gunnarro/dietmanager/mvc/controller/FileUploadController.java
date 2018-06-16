package com.gunnarro.dietmanager.mvc.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
public class FileUploadController {

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
    public ModelAndView listUploadedFiles(@PathVariable String id) throws IOException {
        List<String> files = fileUploadService.loadAll(id)
                .map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "getImageAsResource", id, path.getFileName().toString()).build().toString())
                .collect(Collectors.toList());
        ModelAndView modelView = new ModelAndView("upload/upload-file");
        modelView.getModel().put("id", id);
        modelView.getModel().put("files", files);
        return modelView;
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
     * @param file
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description, @RequestParam("id") String id,
            RedirectAttributes redirectAttributes) {
    	if (file == null) {
    		// return error
    		return "redirect:/upload/" + id;
    	}
    	
        fileUploadService.store(file, id, description);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/upload/" + id;
    }

    // @ExceptionHandler(UploadFileNotFoundException.class)
    // public ResponseEntity<?>
    // handleStorageFileNotFound(UploadFileNotFoundException exc) {
    // return ResponseEntity.notFound().build();
    // }
}
