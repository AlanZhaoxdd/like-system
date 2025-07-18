package com.example.likesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.likesystem.model.entity.Blog;
import com.example.likesystem.service.BlogService;
import com.example.likesystem.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author alanz
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




