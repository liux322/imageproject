package com.image.imageproject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.imageproject.repository.ImageRepository;
import com.image.imageproject.repository.entity.Image;
import com.image.imageproject.utils.SSLUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Service
public class LoadImageService {
    private static final Logger LOG = LoggerFactory.getLogger(LoadImageService.class);
  //  private static final String URL = "https://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magic";
    private static final String TOTAL_URL = "http://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magicall";


    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ImageRepository imageRepository;


    private RestTemplate restTemplate = new RestTemplate();

    public LoadImageService(){
        try {
            SSLUtil.turnOffSslChecking();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("", e);
        } catch (KeyManagementException e) {
            LOG.error("", e);
        }
    }

    @Cacheable("IMAGES")
    public List<Image> getImagesList(){
        int totalNumber = getTotalNumber();
        System.out.println("total number = " + totalNumber);

       // List<ThirdPartyDto> list = new ArrayList<>();
        long id = 1;
        final long chunkSize = 100;
       // while(getFoundSize() < 50) {
        while(getFoundSize() < totalNumber){
            LoadImageThread thread = applicationContext.getBean(LoadImageThread.class);
            thread.setStartId(id);
            thread.setEndId(id+chunkSize);
            taskExecutor.execute(thread);
            id += chunkSize;
        }

        ((ThreadPoolTaskExecutor)taskExecutor).shutdown();

        List<Image> allImages = imageRepository.findAll();
        return allImages;

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

    private int getFoundSize(){
        List<Image> allImages = imageRepository.findAll();
        int size = allImages.size();
        System.out.println("the image size = " + size);
        return size;
    }

}
