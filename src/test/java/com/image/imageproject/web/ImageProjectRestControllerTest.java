package com.image.imageproject.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.image.imageproject.data.ImageDto;
import com.image.imageproject.data.ThirdPartyDto;
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
        ThirdPartyDto dto1 = ThirdPartyDto.builder()
                .createAt(1524158544)
                .id(1)
                .imageUrl("https://unsplash.it/500?image=1").build();
        ThirdPartyDto dto2 = ThirdPartyDto.builder()
                .createAt(1524158544)
                .id(2)
                .imageUrl("https://unsplash.it/500?image=2").build();
        controller = new ImageProjectRestController(imageService);
        when(imageService.getImagesList()).thenReturn(Arrays.asList(dto1,
                                                                    dto2));
    }

    @Test
    public void test_findAll(){
        List<ImageDto> testList = controller.findAll();
        assertEquals(testList.size(), 2);
    }


}