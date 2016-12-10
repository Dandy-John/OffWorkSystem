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
        ResultWrapper<User> result = isLoginAPI(userVerify);
        if (result.isSuccess()) {
            String error = "您已登陆";
            model.addAttribute("error", error);
            return "error/error";
        }
        else {
            return "user/login";
        }

        /* old implementation
        User user = userService.isLogin(userVerify);
        if (user == null) {
            return "user/login";
        }
        else {
            String error = "您已登录";
            model.addAttribute("error", error);
            return "error/error";
        }
        */
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        //调用相关API取得数据
        ResultWrapper<List<User>> wrapper = listAPI(userVerify);
        if (wrapper.isSuccess()) {
            model.addAttribute("list", wrapper.getData());
            return ("user/list");
        }
        else {
            model.addAttribute("error", ResultStateEnum.stateof(wrapper.getState()).getStateInfo());
            return "error/error";
        }
        /* old implementation
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
        */
    }

    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
    public String edit(@CookieValue(value = "userVerify", required = false) String userVerify,
                       @PathVariable("userId") int userId,
                       Model model) {
        int result = isAdminAPI(userVerify).getState();
        if (result == 200) {
            User user = getByIdAPI(userId).getData();
            List<Department> departmentList = getAllDepartmentsAPI(userVerify).getData();
            model.addAttribute("user", user);
            model.addAttribute("departments", departmentList);
            return "user/edit";
        }
        else {
            String error = ResultStateEnum.stateof(result).getStateInfo();
            model.addAttribute("error", error);
            return "error/error";
        }
        /* old implementation
        int result = userService.verifyCookieOfAdmin(userVerify);

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
        */
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        int permission = isAdminAPI(userVerify).getState();
        if (permission == 200) {
            List<Department> departments = getAllDepartmentsAPI(userVerify).getData();
            model.addAttribute("departments", departments);
            return "user/add";
        }
        else {
            String error = ResultStateEnum.stateof(permission).getStateInfo();
            model.addAttribute("error", error);
            return "error/error";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editMyself(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        ResultWrapper<User> wrapper = isLoginAPI(userVerify);
        if (wrapper.isSuccess()) {
            List<Department> departments = getAllDepartmentsAPI(userVerify).getData();
            model.addAttribute("departments", departments);
            model.addAttribute("user", wrapper.getData());
            return "/user/editMyself";
        }
        else {
            String error = ResultStateEnum.stateof(wrapper.getState()).getStateInfo();
            model.addAttribute("error", error);
            return "/error/error";
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(@CookieValue(value = "userVerify", required = false) String userVerify, Model model) {
        ResultWrapper<User> wrapper = isLoginAPI(userVerify);
        if (wrapper.isSuccess()) {
            model.addAttribute("user", wrapper.getData());
            return "/user/changePassword";
        }
        else {
            String error = ResultStateEnum.stateof(wrapper.getState()).getStateInfo();
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
        int result = userService.verifyCookieOfAdmin(userVerify);
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
    public ResultWrapper<User> editAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                       @ModelAttribute("userId") int userId,
                                       @ModelAttribute("userName") String userName,
                                       @ModelAttribute("userSex") String userSex,
                                       @ModelAttribute("userAge") int userAge,
                                       @ModelAttribute("userDepartment") int userDepartment,
                                       @ModelAttribute("userLeader") int userLeader,
                                       @ModelAttribute("userTimeLeft") int userTimeLeft) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission == 200) {
            int result = userService.updateUser(userId, userName, userSex, userAge, userDepartment, userLeader, userTimeLeft);
            return new ResultWrapper<User>(result, null);
        }
        else {
            return new ResultWrapper<User>(permission, null);
        }
    }

    @RequestMapping(
            value = "/api/reset",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> resetPasswordAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                                @ModelAttribute("userId") int userId) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission == 200) {
            int result = userService.resetPassword(userId);
            return new ResultWrapper<User>(result, null);
        }
        else {
            return new ResultWrapper<User>(permission, null);
        }
    }

    @RequestMapping(
            value = "/api/isAdmin",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> isAdminAPI(@CookieValue(value = "userVerify", required = false) String userVerify) {
        return new ResultWrapper<User>(userService.verifyCookieOfAdmin(userVerify), null);
    }

    @RequestMapping(
            value = "/api/getAllDepartments",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<List<Department>> getAllDepartmentsAPI(@CookieValue("userVerify") String userVerify) {
        if (isLoginAPI(userVerify).isSuccess()) {
            List<Department> departmentList = departmentService.queryAll();
            return new ResultWrapper<List<Department>>(200, departmentList);
        }
        else {
            return new ResultWrapper<List<Department>>(490, null);
        }
    }

    @RequestMapping(
            value = "/api/delete",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> deleteAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                         @ModelAttribute("userId") int userId) {
        int result = isAdminAPI(userVerify).getState();
        if (result == 200) {
            int state = userService.deleteById(userId);
            return new ResultWrapper<User>(state, null);
        }
        else {
            return new ResultWrapper<User>(result, null);
        }
    }

    @RequestMapping(
            value = "/api/add",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> addAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                      @ModelAttribute("userUsername") String userUsername,
                                      @ModelAttribute("userName") String userName,
                                      @ModelAttribute("userSex") String userSex,
                                      @ModelAttribute("userAge") int userAge,
                                      @ModelAttribute("userDepartment") int userDepartment,
                                      @ModelAttribute("userLeader") int userLeader,
                                      @ModelAttribute("userTimeLeft") int userTimeLeft) {
        int permission = isAdminAPI(userVerify).getState();
        if (permission == 200) {
            int result = userService.addUser(userUsername,
                    userName,
                    userSex,
                    userAge,
                    userDepartment,
                    userLeader,
                    userTimeLeft);
            return new ResultWrapper<User>(result, null);
        }
        else {
            return new ResultWrapper<User>(permission, null);
        }
    }

    @RequestMapping(
            value = "/api/isMyself",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> isMyselfAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                        @ModelAttribute("userId") int userId) {
        int isMyself = userService.isMyself(userVerify, userId);
        return new ResultWrapper<User>(isMyself, null);
    }

    @RequestMapping(
            value = "/api/editMyself",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> editMyselfAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                             @ModelAttribute("userName") String userName,
                                             @ModelAttribute("userSex") String userSex,
                                             @ModelAttribute("userAge") int userAge) {
        ResultWrapper<User> wrapper = isLoginAPI(userVerify);
        if (wrapper.isSuccess()) {
            int result = userService.editMyself(wrapper.getData().getUserId(), userName, userSex, userAge);
            return new ResultWrapper<User>(result, null);
        }
        else {
            return new ResultWrapper<User>(490, null);
        }
    }

    @RequestMapping(
            value = "/api/changePassword",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<User> changePasswordAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                                 @ModelAttribute("userId") int userId,
                                                 @ModelAttribute("oldPassword") String oldPassword,
                                                 @ModelAttribute("newPassword") String newPassword) {
        int isMyself = isMyselfAPI(userVerify, userId).getState();
        if (isMyself == 200) {
            int result = userService.changeMyPassword(userId, oldPassword, newPassword);
            return new ResultWrapper<User>(result, null);
        }
        else {
            return new ResultWrapper<User>(isMyself, null);
        }
    }


    //--------------------------------------------------------------------
    //cookie example

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