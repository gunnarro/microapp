package com.gunnarro.imagemanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.imagemanager.domain.ImageDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
// @Ignore
public class ImageManagerRepositoryTest {

    @Autowired
    @Qualifier("imagemanagerRepository")
    private ImageManagerRepository imagemanagerRepository;
 

    @Test
    public void getImages() {
        List<ImageDetail> images = imagemanagerRepository.getImages(99);
        assertNotNull(images);
        assertEquals(8, images.size());
        assertEquals("20160318_210015.jpg", images.get(0).getName());
        assertEquals("title", images.get(0).getTitle());
        assertEquals("/var/lib/openshift/53baabba500446a62b000df6/jbossews/uploads", images.get(0).getFilePath());
        assertEquals("/web/static/gallery/", images.get(0).getMappedAbsoluteFilePath());
        assertEquals(0, images.get(0).getSize().intValue());
        assertEquals("type", images.get(0).getType());
        assertEquals("Kveldsmat, GoMorgen Youghurt byttet ut med 5 fiskekaker", images.get(0).getDescription());
        assertEquals(1, images.get(0).getId().intValue());
        assertEquals("geo location", images.get(0).getGeoLocation());
        assertNotNull(images.get(0).getCreatedDate());
    }

    @Test
    public void CRUDImage() {
        ImageDetail img = new ImageDetail();
        img.setName("unit-test.jpg");
        img.setUserId(3);
        img.setTitle("Middag hjemme");
        img.setCreatedDate(new Date());
        img.setFilePath("/home/user/uploads/images/guest");
        img.setMappedAbsoluteFilePath("/web/upload/galley");
        img.setSize(1234567890L);
        img.setGeoLocation("here");
        img.setDescription("unit-test");
        int imageId = imagemanagerRepository.createImage(img);
        assertTrue(imageId != 0);

        ImageDetail image = imagemanagerRepository.getImage(imageId);
        assertEquals(imageId, image.getId().intValue());
        assertEquals(3, image.getUserId().intValue());
        assertEquals("unit-test.jpg", image.getName());
        assertEquals("Middag hjemme", image.getTitle());
        assertEquals("here", image.getGeoLocation());
        assertEquals("unit-test", image.getDescription());
        assertEquals(".web.upload.galley", image.getCanonicalPath());
        assertEquals("/web/upload/galley", image.getMappedAbsoluteFilePath());
        assertNotNull(image.getCreatedDate());
        assertNotNull(image.getLastModifiedDate());
        assertEquals("/home/user/uploads/images/guest",image.getFilePath());

        image.setTitle("title updated");
        image.setGeoLocation("location updated");
        image.setDescription("description updated");
        imagemanagerRepository.updateImage(image);
        ImageDetail imageUpdated = imagemanagerRepository.getImage(imageId);
        assertEquals(imageId, imageUpdated.getId().intValue());
        assertEquals(3, imageUpdated.getUserId().intValue());
        assertEquals("unit-test.jpg", imageUpdated.getName());
        assertEquals("title updated", imageUpdated.getTitle());
        assertEquals("location updated", imageUpdated.getGeoLocation());
        assertEquals("description updated", imageUpdated.getDescription());
        
        assertTrue(imagemanagerRepository.deleteImage(image.getId()));
    }

}
