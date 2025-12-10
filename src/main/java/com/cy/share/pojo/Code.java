package com.cy.share.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName code
 */
@TableName(value ="code")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @TableId
    private String phone;
    private String code;
}