package com.example.likesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.likesystem.constant.ThumbConstant;
import com.example.likesystem.model.dto.thumb.DoThumbRequest;
import com.example.likesystem.model.entity.Blog;
import com.example.likesystem.model.entity.Thumb;
import com.example.likesystem.model.entity.User;
import com.example.likesystem.service.BlogService;
import com.example.likesystem.service.ThumbService;
import com.example.likesystem.mapper.ThumbMapper;
import com.example.likesystem.service.UserService;
import com.example.likesystem.util.RedisKeyUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
* @author alanz
*/
@Service("thumbServiceDB")
@Slf4j
@RequiredArgsConstructor
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb> implements ThumbService {

    private final UserService userService;

    private final BlogService blogService;

    private final TransactionTemplate transactionTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean doThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
        if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
            throw new RuntimeException("参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        // 加锁
        synchronized (loginUser.getId().toString().intern()) {

            // 编程式事务
            return transactionTemplate.execute(status -> {
                Long blogId = doThumbRequest.getBlogId();
                boolean exists = this.hasThumb(blogId, loginUser.getId());
                if (exists) {
                    throw new RuntimeException("用户已点赞");
                }

                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumbCount = thumbCount + 1")
                        .update();

                Thumb thumb = new Thumb();
                thumb.setUserId(loginUser.getId());
                thumb.setBlogId(blogId);

                boolean success = update && this.save(thumb);

                //点赞记录存入Redis
                if (success) {
                   String key = RedisKeyUtil.getUserThumbKey(loginUser.getId());
                   redisTemplate.opsForHash().put(key, blogId.toString(), thumb.getId());
                }
                // 更新成功才执行
                return success;
            });
        }
    }

    @Override
    public Boolean undoThumb(DoThumbRequest doThumbRequest, HttpServletRequest request) {
        if (doThumbRequest == null || doThumbRequest.getBlogId() == null) {
            throw new RuntimeException("参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        //加锁
        synchronized (loginUser.getId().toString().intern()) {

            //编程式事务
            return transactionTemplate.execute(status -> {
                Long blogId = doThumbRequest.getBlogId();
                String key = RedisKeyUtil.getUserThumbKey(loginUser.getId());
                Object thumbIdObj = redisTemplate.opsForHash().get(key, blogId.toString());
                if (thumbIdObj == null) {
                    throw new RuntimeException("用户未点赞");
                }
                Long thumbId = Long.valueOf(thumbIdObj.toString());

                boolean update = blogService.lambdaUpdate()
                        .eq(Blog::getId, blogId)
                        .setSql("thumbCount = thumbCount - 1")
                        .update();

                boolean success = update && this.removeById(thumbId);

                //点赞记录从Redis删除
                if (success) {
                    redisTemplate.opsForHash().delete(key, blogId.toString());
                }
                return success;
            });
        }
    }

    @Override
    public Boolean hasThumb(Long blogId, Long userId) {
        String key = RedisKeyUtil.getUserThumbKey(userId);
        return redisTemplate.opsForHash().hasKey(key, blogId.toString());
    }
}




