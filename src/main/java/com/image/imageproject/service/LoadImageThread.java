package com.image.imageproject.service;

import com.image.imageproject.data.ThirdPartyDto;
import com.image.imageproject.repository.ImageRepository;
import com.image.imageproject.repository.entity.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class LoadImageThread implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(LoadImageThread.class);
    private static final String URL = "https://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magic";
    private static final String TOTAL_URL = "http://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magicall";

    private Long startId;
    private Long endId;

    @Autowired
    private ImageRepository imageRepository;

    private RestTemplate restTemplate = new RestTemplate();


    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }


    @Override
    public void run() {
        scanForImages();
    }

    private List<ThirdPartyDto> scanForImages() {
        List<ThirdPartyDto> images = new ArrayList<>();
        long currentId = startId;
        while(currentId < endId) {
            LOG.info("trying  image with id {}", currentId);
            ThirdPartyDto image = getImageForId(currentId);
            if(image != null) {
                Image entity = new Image();
                entity.setId(image.getId());
                entity.setImageurl(image.getImageUrl());
                imageRepository.save(entity);
                images.add(image);

            }
            currentId++;
        }
        return images;
    }


    private ThirdPartyDto getImageForId(long id) {
        ThirdPartyDto image = null;
        try {
            LOG.info("requesting " + URL + "/" + id);
            image = restTemplate
                    .getForObject(URL + "/" + id, ThirdPartyDto.class);

        } catch (HttpClientErrorException ex) {
            LOG.warn("HttpClientErrorException");
            image = null;
        } catch (ResourceAccessException e1) {
            LOG.warn("ResourceAccessException");
        }

        return image;
    }
}
