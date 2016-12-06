package org.off_work_system.web;

import org.apache.ibatis.annotations.Param;
import org.off_work_system.dto.ResultWrapper;
import org.off_work_system.entity.Department;
import org.off_work_system.entity.User;
import org.off_work_system.enums.ResultStateEnum;
import org.off_work_system.service.DepartmentService;
import org.off_work_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
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

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        User user = userService.isLogin(userVerify);
        if (user == null) {
            return "user/login";
        }
        else {
            String error = "您已登录";
            model.addAttribute("error", error);
            return "error/error";
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        int result = userService.verifyUserCookieOfListPermission(userVerify);

        if (result == 200) {
            List<User> list = userService.getUserList();
            model.addAttribute("list", list);
            return "user/list";
        }
        else {
            String error = ResultStateEnum.stateof(result).getStateInfo();
            model.addAttribute("error", error);
            return "error/error";
        }
    }

    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
    public String edit(@CookieValue(value = "userVerify", required = false) String userVerify,
                       @PathVariable("userId") int userId,
                       Model model) {
        int result = userService.verifyUserCookieOfListPermission(userVerify);

        if (result == 200) {
            User user = userService.getUser(userId);
            List<Department> departmentList = departmentService.queryAll();
            model.addAttribute("user", user);
            model.addAttribute("departments", departmentList);
            return "user/edit";
        }
        else {
            String error = ResultStateEnum.stateof(result).getStateInfo();
            model.addAttribute("error", error);
            return "error/error";
        }
    }

    //--------------------------------------------------------------------
    //下面的是提供的接口。只使用接口也可以实现与网页端相同的功能


    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper<List<User>> listAPI(@CookieValue(value = "userVerify", required = false) String userVerify) {
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
            value = "/api/{userId}/detail",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> getByIdAPI(@PathVariable("userId") int userId) {
        User user = userService.getUser(userId);
        int result = user == null ? 404 : 200;
        return new ResultWrapper<User>(result, user);
    }

    @RequestMapping(
            value = "/api/login", //测试用，以后会改成 /login
            method = RequestMethod.POST, //测试用，以后会改成POST
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> loginAPI(@ModelAttribute("username") String username,
                                     @ModelAttribute("password") String password,
                                     HttpServletResponse response) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            Date now = new Date();
            String combine = username + "|" + password + "|" + now.getTime();
            //System.out.println(combine);
            Cookie cookie = new Cookie("userVerify", combine);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 15);
            response.addCookie(cookie);
            return new ResultWrapper<User>(200, user);
        }
        else {
            return new ResultWrapper<User>(404, null);
        }
    }

    @RequestMapping(
            value = "/api/islogin",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> isLoginAPI(@CookieValue(value = "userVerify", required = false) String userVerify) {
        User user = userService.isLogin(userVerify);
        if (user != null) {
            return new ResultWrapper<User>(200, user);
        }
        else {
            return new ResultWrapper<User>(490, null);
        }
    }

    @RequestMapping(
            value = "/api/logout",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> logoutAPI(HttpServletResponse response) {
        Cookie cookie = new Cookie("userVerify", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResultWrapper<User>(200, null);
    }

    @RequestMapping(
            value = "/api/edit",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> editAPI(@CookieValue("userVerify") String userVerify,
                                       @ModelAttribute("userId") int userId,
                                       @ModelAttribute("userName") String userName,
                                       @ModelAttribute("userSex") String userSex,
                                       @ModelAttribute("userAge") int userAge,
                                       @ModelAttribute("userDepartment") int userDepartment,
                                       @ModelAttribute("userLeader") int userLeader,
                                       @ModelAttribute("userTimeLeft") int userTimeLeft) {
        int result = userService.updateUser(userId, userName, userSex, userAge, userDepartment, userLeader, userTimeLeft);
        if (result == 0) {
            return new ResultWrapper<User>(600, null);
        }
        else {
            return new ResultWrapper<User>(200, null);
        }
    }

    @RequestMapping(
            value = "/api/trysetcookie",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public int trySetCookieAPI(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "abc");
        cookie.setPath("/");
        response.addCookie(cookie);
        return 0;
    }

    @RequestMapping(
            value = "/api/trygetcookie",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public String tryGetCookieAPI(@CookieValue(value = "username", required = false) String username) {
        return username;
    }

    @RequestMapping(
            value = "/api/trydeletecookie",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public int tryDeleteCookieAPI(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return 0;
    }
}