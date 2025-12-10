package com.cy.share.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryDto {
    private Integer page;
    private Integer pageSize;
    private String author;
}
