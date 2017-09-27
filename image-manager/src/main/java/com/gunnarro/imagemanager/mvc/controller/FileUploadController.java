package com.gunnarro.imagemanager.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.UploadFileInfo;
import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.service.ImageService;
import com.gunnarro.imagemanager.service.exception.ApplicationException;
import com.gunnarro.imagemanager.utility.ImageUtil;

@Controller
public class FileUploadController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
    
    public static final String TUMB_DIR_NAME = "tumbs";
    
    @Autowired
    @Qualifier("imageService")
    protected ImageService imageService;

    @Value("${mapped.gallery.uri:/web/static/gallery/}")
    private String mappedGalleryUri;

    @Value("${uploaddir.absolutepath:uploads/images}")
    private String uploadDirAbsolutePath;

    /**
     * For unite test only
     * 
     * @param imageService
     */
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewFileUploadPage() {
        ModelAndView modelAndView = new ModelAndView("import/upload-file");
        modelAndView.addObject("status", null);
        return modelAndView;
    }

    @RequestMapping(value = "/listuploadedfiles", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listUploadedFiles() {
        Integer userId = LoginController.ANONYMOUS_USER_ID;
        if (authenticationFacade.getLoggedInUser() != null) {
            userId = authenticationFacade.getLoggedInUser().getId();
        }
        ModelAndView modelAndView = new ModelAndView("import/list-upload-files");
        modelAndView.addObject("uploadedImageList", imageService.getImages(userId));
        return modelAndView;
    }

    // @RequestMapping(value = "/deletefile/{path}/{filename:.+}", method =
    // RequestMethod.GET)
    // @ResponseBody
    // public ModelAndView deleteFile(@PathVariable("path") String path,
    // @PathVariable("filename") String fileName) {
    // String status = deleteFile(UploadFileInfo.toPath(path) + File.separator +
    // fileName);
    // ModelAndView modelAndView = new ModelAndView("import/upload-file");
    // modelAndView.addObject("status", status);
    // modelAndView.addObject("uploadedFileList", getUploadedFiles());
    // modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
    // return modelAndView;
    // }

    // @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    // @ResponseBody
    // public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile
    // file) {
    // ModelAndView modelAndView = new ModelAndView("import/upload-file");
    // if (!file.isEmpty()) {
    // try {
    // byte[] bytes = file.getBytes();
    // BufferedOutputStream stream = new BufferedOutputStream(new
    // FileOutputStream(new File(getApplicationRootPath() + File.separator +
    // "uploads" + File.separator
    // + file.getOriginalFilename())));
    // stream.write(bytes);
    // stream.close();
    // modelAndView.addObject("status", "Successfully uploaded " +
    // file.getOriginalFilename());
    // } catch (Exception e) {
    // LOG.error(null, e);
    // modelAndView.addObject("status", "Failed to upload " +
    // file.getOriginalFilename() + " => " + e.getMessage());
    // }
    // } else {
    // modelAndView.addObject("status", "Failed to upload " +
    // file.getOriginalFilename() + " because the file was empty.");
    // }
    // modelAndView.addObject("uploadedFileList", getUploadedFiles());
    // modelAndView.addObject("uploadedImagesList", imageService.getImages(1));
    // return modelAndView;
    // }

    @RequestMapping(value = "/uploadimage", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView handleImageUpload(@RequestParam("file") MultipartFile uploadFile, @RequestParam("description") String description, @RequestParam("title") String title,
            @RequestParam("location") String location) {
        ModelAndView modelAndView = new ModelAndView("import/upload-file");
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Start uploading file for user: " + authenticationFacade.getLoggedInUser());
            }
            User user = imageService.getUser("guest");
            // Check upload directory for this user
            if (authenticationFacade.getLoggedInUser() != null) {
                user = authenticationFacade.getLoggedInUser();
            }
            File userDir = checkUserImageDir(user.getUserName());

            if (LOG.isDebugEnabled()) {
                LOG.debug("save file: " + uploadFile.getOriginalFilename() + ", size: " + uploadFile.getSize() + ", to " + userDir.getAbsolutePath());
            }

            if (!uploadFile.isEmpty()) {
                try {
//                    File imgFile = new File(userDir.getAbsolutePath() + File.separator + uploadFile.getOriginalFilename());
//                    byte[] bytes = uploadFile.getBytes();
//                    if (LOG.isDebugEnabled()) {
//                        LOG.debug("Write image to: " + imgFile.getAbsolutePath());
//                    }
//                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imgFile));
//                    stream.write(bytes);
//                    stream.close();
                    // Then create tumb
//                    File imgFileTumb = new File(userDir.getAbsolutePath() + File.separator + TUMB_DIR_NAME + File.separator + uploadFile.getOriginalFilename());
                    String imagePath = userDir.getAbsolutePath() + File.separator + uploadFile.getOriginalFilename();
                    String imageTumbPath =  userDir.getAbsolutePath() + File.separator + TUMB_DIR_NAME + File.separator + uploadFile.getOriginalFilename();
                    BufferedImage inputImage = ImageIO.read(uploadFile.getInputStream());
                    ImageUtil.resizeToFixedSize(inputImage, imageTumbPath, 48, 48);
                    // Finally resize original image in order to reduce file size. Note, this will reduce the quality
                    ImageUtil.resizePercentage(inputImage, imagePath, 0.75);
                    modelAndView.addObject("status", "Successfully uploaded: " + uploadFile.getOriginalFilename());
                } catch (Exception e) {
                    LOG.error("", e);
                    modelAndView.addObject("status", "Failed to upload " + uploadFile.getOriginalFilename() + " => " + e.getMessage());
                    return modelAndView;
                }
            } else {
                modelAndView.addObject("status", "Failed to upload " + uploadFile.getOriginalFilename() + " because the file was empty.");
            }
            // Update DB
            ImageDetail img = new ImageDetail();
            img.setUserId(user.getId());
            img.setCreatedDate(new Date());
            img.setName(uploadFile.getOriginalFilename());
            img.setFilePath(userDir.getPath());
            // All users have it own catalog for pictures
            img.setMappedAbsoluteFilePath(mappedGalleryUri + user.getUserName());
            img.setDescription(description);
            img.setType("img");
            img.setTitle(title);
            img.setGeoLocation(location);
            img.setSize(uploadFile.getSize());
            imageService.saveImage(img);
            if (LOG.isDebugEnabled()) {
                LOG.debug("sucessfulley saved img to both disk and DB: " + img.getFilePath());
            }
            return new ModelAndView("redirect:/gallery");
        } catch (Exception e) {
            LOG.error("", e);
            modelAndView.addObject("status", "Failed to upload " + uploadFile.getOriginalFilename() + " => " + e.getMessage());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/viewfile/{path}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewFile(@PathVariable("path") String path, @PathVariable("filename") String fileName) {
        ModelAndView modelAndView = new ModelAndView("import/view-file");
        modelAndView.addObject("filecontent", readFile(UploadFileInfo.toPath(path) + File.separator + fileName));
        return modelAndView;
    }

//    private List<UploadFileInfo> getUploadedFiles() {
//        List<UploadFileInfo> list = new ArrayList<UploadFileInfo>();
//        File rootPath = new File(".");
//        File uploadedFile = new File(rootPath.getAbsolutePath() + File.separator + "uploads");
//        if (uploadedFile.exists()) {
//            for (File f : uploadedFile.listFiles()) {
//                UploadFileInfo fileInfo = new UploadFileInfo();
//                fileInfo.setName(f.getName());
//                try {
//                    fileInfo.setAbsolutePath(f.getCanonicalPath());
//                } catch (IOException e) {
//                    LOG.error(null, e);
//                }
//                fileInfo.setSize(f.length());
//                fileInfo.setCreatedTime(f.lastModified());
//                list.add(fileInfo);
//            }
//        }
//        return list;
//    }

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

    protected File checkUserImageDir(String userCatalogName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Application root path: " + getApplicationRootPath());
        }
        if (StringUtils.isEmpty(getUploadDirAbsolutePath())) {
            throw new ApplicationException("Application error, upload dir config not set!");
        }
        createDirectory(getUploadDirAbsolutePath() + File.separator + userCatalogName);
        // then also create a catalog for tumbernails
        createDirectory(getUploadDirAbsolutePath() + File.separator + userCatalogName + File.separator + TUMB_DIR_NAME);
        return new File(getUploadDirAbsolutePath() + File.separator + userCatalogName);
    }

    private void createDirectory(String absoluteDirPath) {
        Path dir = Paths.get(absoluteDirPath);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir, new FileAttribute<?>[0]);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("created directory:" + absoluteDirPath);
                }
            } catch (Exception e) {
                LOG.error("", e);
                throw new ApplicationException("Technical problems! " + e.getMessage());
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Directory already exist: " + absoluteDirPath);
            }
        }
    }

    protected String getUploadDirAbsolutePath() {
        return uploadDirAbsolutePath;
    }

    protected String getMappedGalleryUri() {
        return mappedGalleryUri;
    }

    public void setMappedGalleryUri(String mappedGalleryUri) {
        this.mappedGalleryUri = mappedGalleryUri;
    }

    private String getApplicationRootPath() {
        // System.out.println("......: " + System.getProperty("catalina_home"));
        return new File(".").getAbsolutePath().replace("\\.", "");
    }
}
