package com.example.likesystem.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 视图包装类
 */
@Data
public class BlogVO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String coverImg;

    /**
     * 内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer thumbCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否已点赞
     */
    private Boolean hasThumb;
}
