package org.xyafu.wallpaper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyafu.wallpaper.mapper.UserMapper;
import org.xyafu.wallpaper.pojo.ResultInfo;
import org.xyafu.wallpaper.pojo.User;
import org.xyafu.wallpaper.utils.UuidUtil;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户是否是登录状态
     * @param session
     * @return
     */
    @GetMapping("isLogin")
    public ResultInfo isLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return new ResultInfo(false,"用户未登录");
        }
        return new ResultInfo(true,user,"用户已登录");
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public ResultInfo login(User user,HttpSession session){
        if(user==null){
            return new ResultInfo(false,"当前用户未登录");
        }
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_name",user.getUserName());
        wrapper.eq("user_password",user.getUserPassword());
        User loginUser = userMapper.selectOne(wrapper);
        if(loginUser!=null){
            session.setAttribute("user",loginUser);
            return new ResultInfo(true,"登录成功");
        }
        return new ResultInfo(false,"登录失败");
    }


    /**
     * 用户注册
     */
    @PostMapping("register")
    public ResultInfo register(User user,HttpSession session){
        //判断用户名是否为已存在
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name",user.getUserName());
       if( userMapper.selectOne(wrapper)!=null){
           return new ResultInfo(false,"用户已存在");
       }
       user.setUserId(UuidUtil.getUuid());
        int insert = userMapper.insert(user);
        if(insert>0){
            session.setAttribute("user",user);
            return new ResultInfo(true,"注册成功");
        }
        return new ResultInfo(false,"网路出现问题请联系管理员处理");
    }


    @GetMapping("quite")
    public void quite(HttpSession session){
        //初始化session数据
        session.invalidate();
    }


}
