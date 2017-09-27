package com.gunnarro.imagemanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.UploadFileInfo;
import com.gunnarro.imagemanager.service.ImageService;
import com.gunnarro.imagemanager.service.exception.ApplicationException;

public class GalleryControllerTest extends SpringTestSetup {

    @Autowired
	private GalleryController controller;
	
    @Mock
    private ImageService imageServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller.setImageService(imageServiceMock);
    }

    @Test
    public void viewGallery() throws Exception {
        ModelAndView modelAndView = controller.viewGallery();
        Assert.assertEquals("gallery/view-gallery", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<ImageDetail> images = (List<ImageDetail>) modelAndView.getModel().get("imageDetails");
        Assert.assertNotNull(images);
    }
    
    @Test
    public void viewImage() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(5);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        ModelAndView modelAndView = controller.viewImage(22);
        Assert.assertEquals("gallery/view-image", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        ImageDetail image = (ImageDetail) modelAndView.getModel().get("imageDetail");
        Assert.assertNotNull(image);
    }
    
    @Test(expected=ApplicationException.class)
    public void editImageNotFound() throws Exception {
        controller.initUpdateImageDetailForm(22, new ExtendedModelMap());
    }
    
    @Test(expected=ApplicationException.class)
    public void deleteImageNotFound() throws Exception {
       controller.deleteImage(22);
    }
    
    @Test(expected=ApplicationException.class)
    public void editImageNotAuthorized() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(5);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        controller.initUpdateImageDetailForm(22, new ExtendedModelMap());
    }
    
    @Test(expected=ApplicationException.class)
    public void editImagePostNotAuthorized() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(5);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        controller.processUpdateImageDetailForm(imageDetail, null, null);
    }
    
    @Test(expected=ApplicationException.class)
    public void deleteImageNotAuthorized() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(5);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        controller.deleteImage(22);
    }
    
    @Test
    public void editImageInitForm() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(LoginController.ANONYMOUS_USER_ID);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        String url = controller.initUpdateImageDetailForm(22, new ExtendedModelMap());
        Assert.assertEquals("gallery/edit-image-details", url);
    }
    
    @Test
    public void deleteImage() throws Exception {
        ImageDetail imageDetail = new ImageDetail();
        imageDetail.setId(22);
        imageDetail.setUserId(LoginController.ANONYMOUS_USER_ID);
        when(imageServiceMock.getImage(22)).thenReturn(imageDetail);
        String redirectUrl = controller.deleteImage(22);
        Assert.assertEquals("redirect:/gallery", redirectUrl);
    }

}