package com.gunnarro.sportsteam.mvc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.UploadFileInfo;
import com.gunnarro.sportsteam.service.ImageService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
public class FileUploadController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    @Qualifier("imageService")
    protected ImageService imageService;
    
    
    /**
     * For unite test only
     * 
     * @param imageService
     */
    public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	private String deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return "Deleted file! path=" + path;
        }
        return "File Not Found! path=" + path;
    }

    @RequestMapping(value = "/deletefile/{path}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteFile(@PathVariable("path") String path, @PathVariable("filename") String fileName) {
        String status = deleteFile(UploadFileInfo.toPath(path) + File.separator + fileName);
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/upload-file");
        modelAndView.addObject("status", status);
        modelAndView.addObject("uploadedFileList", getUploadedFiles());
        modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
        return modelAndView;
    }

    private List<UploadFileInfo> getUploadedFiles() {
        List<UploadFileInfo> list = new ArrayList<UploadFileInfo>();
        File rootPath = new File(".");
        File uploadedFile = new File(rootPath.getAbsolutePath() + File.separator + "uploads");
        if (uploadedFile.exists()) {
            for (File f : uploadedFile.listFiles()) {
                UploadFileInfo fileInfo = new UploadFileInfo();
                fileInfo.setName(f.getName());
                try {
                    fileInfo.setAbsolutePath(f.getCanonicalPath());
                } catch (IOException e) {
                    LOG.error(null, e);
                }
                fileInfo.setSize(f.length());
                fileInfo.setCreatedTime(f.lastModified());
                list.add(fileInfo);
            }
        }
        return list;
    }
    
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/upload-file");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(getApplicationRootPath() + File.separator + "uploads" + File.separator
                        + file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                modelAndView.addObject("status", "Successfully uploaded " + file.getOriginalFilename());
            } catch (Exception e) {
                LOG.error(null, e);
                modelAndView.addObject("status", "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            modelAndView.addObject("status", "Failed to upload " + file.getOriginalFilename() + " because the file was empty.");
        }
        modelAndView.addObject("uploadedFileList", getUploadedFiles());
        modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
        return modelAndView;
    }

    
    @RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView handleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) {
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/upload-file");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(getApplicationRootPath() + File.separator + "uploads" + File.separator
                        + file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                modelAndView.addObject("status", "Successfully uploaded " + file.getOriginalFilename());
            } catch (Exception e) {
                LOG.error(null, e);
                modelAndView.addObject("status", "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            modelAndView.addObject("status", "Failed to upload " + file.getOriginalFilename() + " because the file was empty.");
        }
        // Update DB
        ImageDetail img = new ImageDetail();
        img.setName(file.getOriginalFilename());
        img.setMappedAbsoluteFilePath("/web/static/gallery/");
        img.setDescription(description);
        img.setType("img");
        img.setSize(file.getSize());
        imageService.saveImage(1, img);
        modelAndView.addObject("uploadedFileList", getUploadedFiles());
        modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
        return modelAndView;
    }
    
    @RequestMapping(value = "/loaddata/{path}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView loadData(@PathVariable("path") String path, @PathVariable("filename") String fileName) {
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/upload-file");
        String filePath = null;
        try {
            filePath = UploadFileInfo.toPath(path);
            boolean isOk = sportsTeamService.loadData(filePath, fileName);
            modelAndView.addObject("status", "Loaded " + filePath + File.separator + fileName + ", status " + (isOk ? "OK" : "Failed"));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            modelAndView.addObject("status", "ERROR: path: " + filePath + File.separator + fileName + ", Exception:" + ae.getMessage());
        }
        modelAndView.addObject("uploadedFileList", getUploadedFiles());
        modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
        return modelAndView;
    }

    private String readFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                return new String(Files.readAllBytes(Paths.get(path)));
            } catch (IOException e) {
                LOG.error(null, e);
            }
        }
        return "File Not Found! path=" + path;
    }

    @RequestMapping(value = "/viewfile/{path}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewFile(@PathVariable("path") String path, @PathVariable("filename") String fileName) {
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/view-file");
        modelAndView.addObject("filecontent", readFile(UploadFileInfo.toPath(path) + File.separator + fileName));
        return modelAndView;
    }

    @RequestMapping(value = "/importdata", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewFileUploadPage() {
        ModelAndView modelAndView = new ModelAndView("secured/admin/import/upload-file");
        modelAndView.addObject("status", null);
        modelAndView.addObject("uploadedFileList", getUploadedFiles());
        modelAndView.addObject("uploadedImageList", imageService.getImages(1));
        return modelAndView;
    }

    private String getApplicationRootPath() {
        return new File(".").getAbsolutePath();
    }

}
