package com.example.likesystem.service;

import com.example.likesystem.model.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.likesystem.model.vo.BlogVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author alanz
*/
public interface BlogService extends IService<Blog> {

    BlogVO getBlogVOById(long blogId, HttpServletRequest request);

    List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request);



}
