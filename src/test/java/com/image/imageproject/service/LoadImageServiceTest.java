package com.image.imageproject.service;

import static org.mockito.Mockito.when;

import com.image.imageproject.repository.ImageRepository;
import com.image.imageproject.repository.entity.Image;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@RunWith(PowerMockRunner.class)
public class LoadImageServiceTest {
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private TaskExecutor taskExecutor;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private RestTemplate restTemplate;

    private LoadImageService service;

    @Before
    public void init() {

        Image image1 = new Image();
        image1.setId(1);
        image1.setImageurl("https://unsplash.it/500?image=1");

        Image image2 = new Image();
        image2.setId(1);
        image2.setImageurl("https://unsplash.it/500?image=1");
        service = new LoadImageService();

        when(imageRepository.findAll()).thenReturn(Arrays.asList(image1, image2));
      //  when(restTemplate.)
    }

    @Test
    public void test_getImagesList(){
        //List<Image> test = service.getImagesList();
    }


}