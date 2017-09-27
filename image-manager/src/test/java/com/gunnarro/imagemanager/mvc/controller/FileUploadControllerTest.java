package com.gunnarro.imagemanager.mvc.controller;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.imagemanager.domain.UploadFileInfo;
import com.gunnarro.imagemanager.service.ImageService;

public class FileUploadControllerTest extends SpringTestSetup {

    @Autowired
	private FileUploadController controller;
	
    @Mock
    private ImageService imageServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
//        controller = new FileUploadController();
        controller.setImageService(imageServiceMock);
    }

    @Test
    public void viewFileUploadPage() throws Exception {
        ModelAndView modelAndView = controller.viewFileUploadPage();
        Assert.assertEquals("import/upload-file", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
    }

    @Test
    public void listUploadFilesPage() throws Exception {
        ModelAndView modelAndView = controller.listUploadedFiles();
        Assert.assertEquals("import/list-upload-files", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<UploadFileInfo> images = (List<UploadFileInfo>) modelAndView.getModel().get("uploadedImageFileList");
        Assert.assertNull(images);
    }
    
    @Test
    public void uploadAbsolutePath() {
        Assert.assertEquals("target\\uploads\\images", controller.getUploadDirAbsolutePath());
    }
    
    @Test
    public void mappedGalleryUri() {
        Assert.assertEquals("/web/static/gallery/test", controller.getMappedGalleryUri());
    }
    
    @Test
    public void checkUserDir() {
        Assert.assertEquals("target\\uploads\\images\\gunnarro-test", controller.checkUserImageDir("gunnarro-test").getPath());
        Assert.assertTrue(new File("target\\uploads\\images\\gunnarro-test\\tumbs").exists());
    }
}