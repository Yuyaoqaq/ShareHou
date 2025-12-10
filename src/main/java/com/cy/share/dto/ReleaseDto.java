package com.cy.share.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDto {
    private Integer id;
    private String picurl;
    private String title;
    private String love;
    private String author;
    private String info;
}
