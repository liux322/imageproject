package com.image.imageproject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.imageproject.data.ThirdPartyDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadImageService {
    private static final Logger LOG = LoggerFactory.getLogger(LoadImageService.class);
    private static final String URL = "http://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magic";
    private static final String TOTAL_URL = "http://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magicall";

    private RestTemplate restTemplate = new RestTemplate();



    @Cacheable("IMAGES")
    public List<ThirdPartyDto> getImagesList(){
        int totalNumber = getTotalNumber();
        System.out.println("total number = " + totalNumber);

        List<ThirdPartyDto> list = new ArrayList<>();
        int id = 1;
        while(list.size() < totalNumber){
            ThirdPartyDto image  = getImageForId(id);

            if(image != null){
                list.add(image);
            }
            id++;

            System.out.println("list size = " +list.size());
        }

        return list;

    }

    private ThirdPartyDto getImageForId(int id) {
        ThirdPartyDto image = null;
        try {
            System.out.println("requesting " + URL + "/" + id);
            image = restTemplate
                    .getForObject(URL + "/" + id, ThirdPartyDto.class);

        } catch (HttpClientErrorException ex) {
            image = null;
        } catch (ResourceAccessException e1) {
                LOG.warn("ResourceAccessException");
        }
        return image;
    }

    private int getTotalNumber()  {
        int total = 0;
        try{
            ResponseEntity<String> response
                    = restTemplate.getForEntity(TOTAL_URL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode totalNode = root.path("total");
            total = totalNode.asInt();

        }catch ( Exception e){

        }
        return total;
    }


}
