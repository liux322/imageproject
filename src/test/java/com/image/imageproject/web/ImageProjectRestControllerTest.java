package com.image.imageproject.web;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.image.imageproject.data.ImageDto;
import com.image.imageproject.repository.entity.Image;
import com.image.imageproject.service.LoadImageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class ImageProjectRestControllerTest {

    @Autowired
    public WebApplicationContext context;

    @Mock
    private LoadImageService imageService;


    private ImageProjectRestController controller;

    @Before
    public void init() {

        Image image1 = new Image();
        image1.setId(1);
        image1.setImageurl("https://unsplash.it/500?image=1");

        Image image2 = new Image();
        image2.setId(1);
        image2.setImageurl("https://unsplash.it/500?image=1");
        controller = new ImageProjectRestController(imageService);
        when(imageService.getImagesList()).thenReturn(Arrays.asList(image1, image2));
    }

    @Test
    public void test_findAll(){
       List<ImageDto> testList = controller.findAll();
        assertEquals(testList.size(), 2);
    }
}