package org.off_work_system.web;

import org.apache.ibatis.annotations.Param;
import org.off_work_system.dto.ResultWrapper;
import org.off_work_system.entity.User;
import org.off_work_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<List<User>> list(@CookieValue(value = "userVerify", required = false) String userVerify) {
        if (userVerify == null) {
            return new ResultWrapper<List<User>>(490, null);
        }
        int result = userService.verifyUserCookieOfListPermission(userVerify);
        if (result != 200) {
            return new ResultWrapper<List<User>>(result, null);
        }

        List<User> list = userService.getUserList();
        return new ResultWrapper<List<User>>(200, list);
    }

    @RequestMapping(
            value = "/{userId}/detail",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> getById(@PathVariable("userId") int userId) {
        User user = userService.getUser(userId);
        int result = user == null ? 404 : 200;
        return new ResultWrapper<User>(result, user);
    }

    @RequestMapping(
            value = "/login/{username}/{password}", //测试用，以后会改成 /login
            method = RequestMethod.GET, //测试用，以后会改成POST
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> login(@PathVariable("username") String username,
                                     @PathVariable("password") String password,
                                     HttpServletResponse response) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            Date now = new Date();
            String combine = username + "|" + password + "|" + now.getTime();
            //System.out.println(combine);
            Cookie cookie = new Cookie("userVerify", combine);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResultWrapper<User>(200, user);
        }
        else {
            return new ResultWrapper<User>(404, null);
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("userVerify", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new ResultWrapper<User>(200, null);
    }

    @RequestMapping(
            value = "/trysetcookie",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public int trySetCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "abc");
        response.addCookie(cookie);
        return 0;
    }

    @RequestMapping(
            value = "/trygetcookie",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public String tryGetCookie(@CookieValue(value = "username", required = false) String username) {
        return username;
    }
}
