package com.image.imageproject.web;

import com.image.imageproject.data.ImageDto;
import com.image.imageproject.data.ThirdPartyDto;
import com.image.imageproject.service.LoadImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/my-images")
public class ImageProjectRestController {
    @Autowired
    private LoadImageService imageService;

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

//        try {
//            response = restTemplate.exchange(url, HttpMethod.GET, entity, responseClass);
//        } catch (HttpStatusCodeException e) {
//            ResponseBody responseBody = new ResponseBody();
//            try {
//                responseBody = new ObjectMapper().readValue(e.getResponseBodyAsByteArray(), ResponseBody.class);
//            } catch (IOException e1) {
//                log.warn("Unable to map response body");
//            }
//            log.debug("{} request to {} returned {}", "GET", responseBody.getPath(), responseBody.getStatus());
//            log.debug("Error message: {}", responseBody.getMessage());
//        }

        return list;
        // return null;
        //RestTemplate
    }


}
