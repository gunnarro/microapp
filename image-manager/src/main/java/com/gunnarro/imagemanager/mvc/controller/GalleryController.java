package com.gunnarro.imagemanager.mvc.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.service.ImageService;
import com.gunnarro.imagemanager.service.exception.ApplicationException;

@Controller
@Scope("session")
public class GalleryController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    @Qualifier("imageService")
    protected ImageService imageService;

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewGallery() {
        Integer userId = super.getLoggedInUserId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("get gallery for userid: " + userId);
        }
        ModelAndView modelView = new ModelAndView("gallery/view-gallery");
        List<ImageDetail> images = imageService.getImages(userId);
        modelView.getModel().put("galleryName", "Gallery");
        modelView.getModel().put("imageDetails", images);
        return modelView;
    }

    @RequestMapping(value = "/image/view/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewImage(@PathVariable("id") int id) {
        Integer userId = super.getLoggedInUserId();
        if (LOG.isDebugEnabled()) {
            LOG.debug("get image for userId: " + userId);
        }
        ModelAndView modelView = new ModelAndView("gallery/view-image");
        modelView.getModel().put("imageDetail", imageService.getImage(id));
        return modelView;
    }

    // ---------------------------------------------
    // Update image information
    // ---------------------------------------------

    @RequestMapping(value = "/image/edit/{id}", method = RequestMethod.GET)
    public String initUpdateImageDetailForm(@PathVariable("id") int id, Model model) {
        ImageDetail imageDetail = imageService.getImage(id);
        if (imageDetail == null) {
            throw new ApplicationException("Object Not Found, imageId=" + id);
        }
        Integer userId = super.getLoggedInUserId();
        if (imageDetail.getUserId() != userId) {
            throw new ApplicationException("Not Authorizeed to edit this image!");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(imageDetail.toString());
        }
        model.addAttribute("imageDetail", imageDetail);
        return "gallery/edit-image-details";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/image/edit/{id}", method = RequestMethod.PUT)
    public String processUpdateImageDetailForm(@Valid @ModelAttribute("imageDetails") ImageDetail imageDetail, BindingResult result, SessionStatus status) {
        Integer userId = super.getLoggedInUserId();
        if (imageDetail.getUserId() != userId) {
            throw new ApplicationException("Not Authorizeed to edit this image!");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(imageDetail.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "gallery/edit-image-details";
        } else {
            imageService.saveImage(imageDetail);
            status.setComplete();
            return "redirect:/gallery";
        }
    }

    @RequestMapping(value = "/image/delete/{id}", method = RequestMethod.GET)
    public String deleteImage(@PathVariable("id") Integer id) {
        Integer userId = super.getLoggedInUserId();
        ImageDetail imageDetail = imageService.getImage(id);
        if (imageDetail == null) {
            throw new ApplicationException("Object Not Found, imageId=" + id);
        }
        if (imageDetail.getUserId() != userId) {
            throw new ApplicationException("Not Authorizeed to delete this image!");
        }
        // First delete image entry in DB
        boolean deleteImage = imageService.deleteImage(id);
        String status = deleteFile(imageDetail.getFilePath(), imageDetail.getName());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Deleted image, status file:" + status + ", removed from DB: " + deleteImage);
        }
        return "redirect:/gallery";
    }

    private String deleteFile(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            boolean delImg = file.delete();
            // Must delete tumbernail also
            File tumb = new File(path + File.separator + FileUploadController.TUMB_DIR_NAME + File.separator + fileName);
            boolean delTumb = tumb.delete();
            if (LOG.isDebugEnabled()) {
                LOG.debug("deleted: " + delImg + ", " + file.getPath());
                LOG.debug("deleted: " + delTumb + ", " + tumb.getPath());
            }
            return "Deleted file! path=" + path;
        }
        return "File Not Found! path=" + path + ", read:" + file.canRead() + ", exe: " + file.canWrite();
    }

    /**
     * For unite test only
     * 
     * @param imageService
     */
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

}
