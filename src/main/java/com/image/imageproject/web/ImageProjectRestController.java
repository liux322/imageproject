package com.image.imageproject.web;

import com.image.imageproject.data.ImageDto;
import com.image.imageproject.data.ThirdPartyDto;
import com.image.imageproject.service.LoadImageService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/my-images")
public class ImageProjectRestController {

    private  LoadImageService imageService;

    public ImageProjectRestController (LoadImageService imageService){
        this.imageService = imageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ImageDto> findAll()  {
        List<ImageDto> list = new ArrayList<>();
        List<ThirdPartyDto> result = imageService.getImagesList();
        if(!result.isEmpty()){
            result.forEach(dto -> {
                ImageDto image = new ImageDto();
                image.setId(dto.getId());
                image.setUrl(dto.getImageUrl());
                list.add(image);
            });
        }

        return list;
    }


}
