package org.off_work_system.web;

import org.off_work_system.dto.ResultWrapper;
import org.off_work_system.entity.Department;
import org.off_work_system.entity.Form;
import org.off_work_system.entity.User;
import org.off_work_system.enums.FormTypeEnum;
import org.off_work_system.enums.ResultStateEnum;
import org.off_work_system.service.DepartmentService;
import org.off_work_system.service.FormService;
import org.off_work_system.service.UserService;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by hui on 16-12-14.
 *
 */
@Controller
@RequestMapping("/model")
public class ModelController {


    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FormService formService;

//    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
//    public String applyHoliday(@CookieValue(value = "userVerify", required = false) String userVerify,
//                       @PathVariable("userId") int userId,
//                       Model model) {
//        return "model/";
//    }

    @RequestMapping(value="/applyHoliday", method = RequestMethod.GET)
    public String applyHoliday(@CookieValue(value = "userVerify", required = false)String userVerify,
                               Model model){
        List<FormTypeEnum> list = new ArrayList<FormTypeEnum>();
        Collections.addAll(list, FormTypeEnum.values());
        model.addAttribute("user", isLoginAPI(userVerify).getData());
        model.addAttribute("typeList", list);
        return "/model/applyHoliday";
    }

    @RequestMapping(value = "/api/addApplyHoliday", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper<Object> addApplyHolidayAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                                    @ModelAttribute("formType") int formType,
                                                    @ModelAttribute("formStartTime")String formStartTimeS,
                                                    @ModelAttribute("formEndTime")String formEndTimeS){
        Date formStartTime = formatDate(formStartTimeS);
        Date formEndTime = formatDate(formEndTimeS);
        if(formStartTime == null || formEndTime == null) return new ResultWrapper<Object>(400, null);
        final int nd = 1000 * 24 * 60 * 60;
        int length = (int)(formEndTime.getTime() - formStartTime.getTime()) / nd + 1;
        ResultWrapper<User> ruser = isLoginAPI(userVerify);
        if(!ruser.isSuccess() || ruser.getData() == null) return new ResultWrapper<Object>(ruser.getState(), null);
        User user = ruser.getData();
        int res = formService.addForm(user.getUserId(), formType, length, formStartTime, formEndTime);
        return new ResultWrapper<Object>(res, null);
    }

    @RequestMapping(value="/personalInfo", method = RequestMethod.GET)
    public String personalInfo(@CookieValue(value = "userVerify", required = false) String userVerify,
                               Model model){
        ResultWrapper<User> res = isLoginAPI(userVerify);
        User user = res.getData();
        if(user == null || res.getState() != 200) model.addAttribute("state", res.getState());
        else {
            model.addAttribute("user", user);
        }
        return "/model/personalInfo";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response, Model model){
        ResultWrapper<User> res = logoutAPI(response);
        assert model != null;
        model.addAttribute("state", res.getState());
        return "/user/login";
    }

    @RequestMapping(value="/queryHoliday", method = RequestMethod.GET)
    public String queryHoliday(@CookieValue(value = "userVerify", required = false) String userVerify,
                               Model model){
        ResultWrapper<List<Form>> res = myApplicationList(userVerify);
        model.addAttribute("list", res.getData());
        model.addAttribute("state", res.getState());
        return "/model/queryHoliday";
    }

    @RequestMapping(value="/approvalHoliday", method = RequestMethod.GET)
    public String approvalHoliday(@CookieValue(value = "userVerify", required = false) String userVerify,
                                  Model model){
        ResultWrapper<List<Form>> res = approveList(userVerify);
        model.addAttribute("state", res.getState());
        if(res.isSuccess()){
            List<Form> formList = res.getData();
            List<Form> list = new ArrayList<Form>();
            for(Form form :formList){
                ResultWrapper<String> r = getNameByIdAPI(userVerify, form.getUserId());
                User user = new User();
                user.setUserName(r.getData());
                form.setUser(user);
                list.add(form);
            }

            model.addAttribute("list", list);
        }
        return "/model/approvalHoliday";
    }

    @RequestMapping(value="/api/approvalHoliday",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResultWrapper<String> getNameByIdAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                  int id){
        User user = userService.isLogin(userVerify);
        if(user == null) return new ResultWrapper<String>(ResultStateEnum.NOT_LOGIN.getState(), null);
        user = userService.getUser(id);
        if(user == null) return new ResultWrapper<String>(ResultStateEnum.NOT_FOUND.getState(), null);
        return new ResultWrapper<String>(ResultStateEnum.OK.getState(), user.getUserName());
    }

    @RequestMapping(value="/api/addApprovalHoliday", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper addApproveHolidayAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                           @ModelAttribute("formId") int formId){
        ResultWrapper<User> res = isLoginAPI(userVerify);
        User user = res.getData();
        if(!res.isSuccess() || user == null) return new ResultWrapper<Object>(res.getState(), null);
        return new ResultWrapper<Object>(formService.advanceForm(user.getUserId(), formId), null);
    }

    @RequestMapping(value="/api/rejectApprovalHoliday", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper rejectApproveHoliday(@CookieValue(value = "userVerify", required = false) String userVerify,
                                           @ModelAttribute("formId") int formId){
        ResultWrapper<User> res = isLoginAPI(userVerify);
        User user = res.getData();
        if(!res.isSuccess() || user == null) return new ResultWrapper<Object>(res.getState(), null);
        return new ResultWrapper<Object>(formService.rejectForm(user.getUserId(), formId), null);
    }

    private Date formatDate(String dateS){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    //--------------------------------------------------------------------
    //下面的是提供的接口。只使用接口也可以实现与网页端相同的功能


    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ResponseBody
    //返回所有的用户列表，需要cookie存储的登录信息是具有用户管理权限的用户
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
    //传入用户的id，返回用户信息，需要cookie中有具有管理员权限的用户
    public ResultWrapper<User> getByIdAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                          @ModelAttribute("userId") int userId) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission != 200) {
            return new ResultWrapper<User>(permission, null);
        }
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
    //传入用户名和密码，如果用户名和密码正确，则设置相应的cookie。返回登录结果状态码
    public ResultWrapper<User> loginAPI(@ModelAttribute("username") String username,
                                        @ModelAttribute("password") String password,
                                        HttpServletResponse response) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            Date now = new Date();
            String combine = user.getUserUsername() + "|" + user.getUserPassword() + "|" + now.getTime();
            //System.out.println(combine);
            Cookie cookie = new Cookie("userVerify", combine);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 15);
            response.addCookie(cookie);
            return new ResultWrapper<User>(200, user);
        }
        else {
            return new ResultWrapper<User>(603, null);
        }
    }

    @RequestMapping(
            value = "/api/islogin",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //根据cookie判断是否已登录，返回登录状态码
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
    //消除登录cookie
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
    //编辑用户，更新用户信息，更新的用户的id为传入的id
    public ResultWrapper<User> editAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                       @ModelAttribute("userId") int userId,
                                       @ModelAttribute("userName") String userName,
                                       @ModelAttribute("userSex") String userSex,
                                       @ModelAttribute("userAge") int userAge,
                                       @ModelAttribute("userDepartment") int userDepartment,
                                       @ModelAttribute("userLeader") int userLeader,
                                       @ModelAttribute("userTimeLeft") int userTimeLeft,
                                       @ModelAttribute("isAdmin") int isAdmin) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission == 200) {
            int result = userService.updateUser(userId, userName, userSex, userAge, userDepartment, userLeader, userTimeLeft, isAdmin);
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
    //传入id,将该id的用户的密码重置为六个零，需要cookie中有权限为管理员的用户
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
    //判断当前cookie中存储的用户是否具有管理员权限。（三个顶层部门的领导具有管理员权限）
    public ResultWrapper<User> isAdminAPI(@CookieValue(value = "userVerify", required = false) String userVerify) {
        return new ResultWrapper<User>(userService.verifyCookieOfAdmin(userVerify), null);
    }

    @RequestMapping(
            value = "/api/getAllDepartments",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //获取所有的部门信息，需要当前cookie中是合法的未过期的登录用户
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
    //输入用户id，删除该id的用户，需要cookie中存储的是具有管理员权限的用户
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
    //添加一个新用户，需要cookie中是具有管理员权限的用户
    public ResultWrapper<User> addAPI(@CookieValue(value = "userVerify", required = false) String userVerify,
                                      @ModelAttribute("userUsername") String userUsername,
                                      @ModelAttribute("userName") String userName,
                                      @ModelAttribute("userSex") String userSex,
                                      @ModelAttribute("userAge") int userAge,
                                      @ModelAttribute("userDepartment") int userDepartment,
                                      @ModelAttribute("userLeader") int userLeader,
                                      @ModelAttribute("userTimeLeft") int userTimeLeft,
                                      @ModelAttribute("isAdmin") int isAdmin) {
        int permission = isAdminAPI(userVerify).getState();
        if (permission == 200) {
            int result = userService.addUser(userUsername,
                    userName,
                    userSex,
                    userAge,
                    userDepartment,
                    userLeader,
                    userTimeLeft,
                    isAdmin);
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
    //输入一个id，输出这个id与当前cookie中的用户是否是同一个人
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
    //编辑自己的信息，需要cookie中具有已登录的用户
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
    //输入用户id、旧密码和新密码,更改密码，需要输入的id与cookie中存储的登录用户是同一个人
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

    @RequestMapping(
            value = "/api/approveList",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //获取所有需要自己审批的申请信息，需要cookie中存储有具有管库员权限的用户
    public ResultWrapper<List<Form>> approveList(
            @CookieValue(value = "userVerify", required = false) String userVerify) {
        ResultWrapper<User> wrapper = isLoginAPI(userVerify);
        if (!wrapper.isSuccess()) {
            return new ResultWrapper<List<Form>>(wrapper.getState(), null);
        }
        else {
            List<Form> formList = formService.showVisibleFormList(wrapper.getData().getUserId());
            return new ResultWrapper<List<Form>>(200, formList);
        }
    }

    @RequestMapping(
            value = "/api/myApplicationList",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //查看自己的所有请假申请，需要cookie中存储有登录的用户
    public ResultWrapper<List<Form>> myApplicationList(
            @CookieValue(value = "userVerify", required = false) String userVerify) {
        ResultWrapper<User> wrapper = isLoginAPI(userVerify);
        if (!wrapper.isSuccess()) {
            return new ResultWrapper<List<Form>>(wrapper.getState(), null);
        }
        else {
            List<Form> formList = formService.showMyFormList(wrapper.getData().getUserId());
            return new ResultWrapper<List<Form>>(200, formList);
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
