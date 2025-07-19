package com.example.likesystem.controller;

import com.example.likesystem.common.BaseResponse;
import com.example.likesystem.common.ResultUtils;
import com.example.likesystem.model.entity.Blog;
import com.example.likesystem.model.vo.BlogVO;
import com.example.likesystem.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author alanz
 */
@RestController
@RequestMapping("blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    @GetMapping("/get")
    public BaseResponse<BlogVO> get(Long blogId, HttpServletRequest request) {
        BlogVO blogVO = blogService.getBlogVOById(blogId, request);
        return ResultUtils.success(blogVO);
    }

    @GetMapping("/list")
    public BaseResponse<List<BlogVO>> list(HttpServletRequest request) {
        List<Blog> blogList = blogService.list();
        List<BlogVO> blogVOList = blogService.getBlogVOList(blogList, request);
        return ResultUtils.success(blogVOList);
    }
}
