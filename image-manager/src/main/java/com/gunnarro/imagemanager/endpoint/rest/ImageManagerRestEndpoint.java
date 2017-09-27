package com.gunnarro.imagemanager.endpoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.imagemanager.service.ImageService;

@RestController
@RequestMapping("/rest/sportsteam")
public class ImageManagerRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ImageManagerRestEndpoint.class);

    @Autowired
    @Qualifier("imageService")
    private ImageService imageService;
    
    /**
     * default constructor, used by spring
     */
    public ImageManagerRestEndpoint() {
    }

  
}
