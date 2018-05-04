package com.image.imageproject.web;

import com.image.imageproject.data.ImageDto;
import com.image.imageproject.repository.entity.Image;
import com.image.imageproject.service.LoadImageService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/my-images")
public class ImageProjectRestController {

    private LoadImageService imageService;

    public ImageProjectRestController(LoadImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ImageDto> findAll() {
        List<ImageDto> list = new ArrayList<>();
        List<Image> result = imageService.getImagesList();
        if (!result.isEmpty()) {
            result.forEach(entity -> {
                ImageDto image = new ImageDto(entity.getId(), entity.getImageurl());
                list.add(image);
            });
        }

        return list;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/page/{pageNumber}/size/{size}")
    public List<ImageDto> findByPage(@PathVariable("pageNumber") int pageNumber, @PathVariable("size") int size) {
        int totalImageNumber = imageService.getTotalNumber();
        if (pageNumber <= 0 || (pageNumber) * size > totalImageNumber) {
            // no images show
            return new ArrayList<>();
        } else {

            int imageNumber = Math.min(totalImageNumber, (pageNumber + 1) * size);
            List<Image> result = imageService.getImageList(imageNumber);

            List<ImageDto> list = new ArrayList<>();
            int startIndex = (pageNumber - 1) * size;
            int endIndex = startIndex + size;
            if ((pageNumber + 1) * size > totalImageNumber && pageNumber * size < totalImageNumber) {
                endIndex = startIndex + (totalImageNumber - pageNumber * size);
            }

            //return the images in the page.
            for (int i = startIndex; i < endIndex; i++) {
                Image entity = result.get(i);
                ImageDto image = new ImageDto(entity.getId(), entity.getImageurl());
                list.add(image);
            }

            return list;
        }

    }
}
