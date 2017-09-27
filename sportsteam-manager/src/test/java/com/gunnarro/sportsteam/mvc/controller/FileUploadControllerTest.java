package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.UploadFileInfo;
import com.gunnarro.sportsteam.service.ImageService;
import com.gunnarro.sportsteam.service.SportsTeamService;

public class FileUploadControllerTest extends SpringTestSetup {

	private FileUploadController controller;
	
    @Mock
    private SportsTeamService sportsTeamServiceMock;
    
    @Mock
    private ImageService imageServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new FileUploadController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        controller.setImageService(imageServiceMock);
    }

    @Test
    public void viewFileUploadPage() throws Exception {
        ModelAndView modelAndView = controller.viewFileUploadPage();
        Assert.assertEquals("secured/admin/import/upload-file", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<UploadFileInfo> list = (List<UploadFileInfo>) modelAndView.getModel().get("uploadedFileList");
        Assert.assertEquals(0, list.size());
        List<UploadFileInfo> images = (List<UploadFileInfo>) modelAndView.getModel().get("uploadedImageFileList");
        Assert.assertNull(images);
    }

    @Test
    public void loadData() throws Exception {
        when(sportsTeamServiceMock.loadData("file/path", "data.xlsx")).thenReturn(true);
        ModelAndView modelAndView = controller.loadData("file\\path", "lillegutt_match_data.xlsx");
        Assert.assertEquals("secured/admin/import/upload-file", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("status"));
        // Assert.assertEquals("Loaded file\\path\\data.xlsx, status Failed",
        // modelAndView.getModel().get("status"));
        List<UploadFileInfo> list = (List<UploadFileInfo>) modelAndView.getModel().get("uploadedFileList");
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void deleteFile() throws Exception {
        ModelAndView modelAndView = controller.deleteFile("file\\path", "data.xlsx");
        Assert.assertEquals("secured/admin/import/upload-file", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("status"));
        // Assert.assertEquals("File Not Found! path=file\\path\\data.xlsx",
        // modelAndView.getModel().get("status"));
        List<UploadFileInfo> list = (List<UploadFileInfo>) modelAndView.getModel().get("uploadedFileList");
        Assert.assertEquals(0, list.size());
    }
}