package com.image.imageproject.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ThirdPartyDto {

    private int id;
    private long createAt;
    private String name;
    private String imageUrl;

}
