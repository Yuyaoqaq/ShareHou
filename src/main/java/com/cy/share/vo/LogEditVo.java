package com.cy.share.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEditVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String picurl;
    private String title;
    private String love;
    private String author;
    private String info;
}
