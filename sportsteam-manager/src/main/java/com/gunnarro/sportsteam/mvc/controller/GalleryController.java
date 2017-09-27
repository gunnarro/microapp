package com.gunnarro.sportsteam.mvc.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.service.ImageService;
import com.gunnarro.sportsteam.utility.ImageFileFilter;

@Controller
@Scope("session")
public class GalleryController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    @Qualifier("imageService")
    protected ImageService imageService;
    
    @RequestMapping(value = "/gallery/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewGallery(@PathVariable("userId") int userId, Map<String, Object> model) {
        ModelAndView modelView = new ModelAndView("gallery/view-gallery");
        List<ImageDetail> images = imageService.getImages(userId);
        modelView.getModel().put("galleryName", "Gallery");
        modelView.getModel().put("imageDetails", images);
        return modelView;
    }

    
//    @RequestMapping(value = "/gallery/uploaded/images", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView viewUploadedImages() {
//        List<String> images = new ArrayList<String>();
//
//        File dir = new File(getApplicationRootPath() + File.separator + "uploads");
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("load images from: " + dir.getPath());
//        }
//        if (dir.exists()) {
//            for (File f : dir.listFiles(new ImageFileFilter())) {
//                images.add(f.getPath());
//            }
//        }
//        ModelAndView modelAndView = new ModelAndView("secured/admin/import/gallery");
//        modelAndView.addObject("imageList", images);
//        modelAndView.addObject("name", "Uploaded Images");
//        return modelAndView;
//    }

}
