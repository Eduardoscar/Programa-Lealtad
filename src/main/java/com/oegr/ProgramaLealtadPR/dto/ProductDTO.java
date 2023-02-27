package com.oegr.ProgramaLealtadPR.dto;

import com.oegr.ProgramaLealtadPR.entity.Business;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;

    private String description;

    private String picture;

    private int cost;

    private long business_id;
}
