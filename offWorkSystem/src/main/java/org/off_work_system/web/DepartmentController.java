package org.off_work_system.web;

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

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/13.
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/department",
            method = RequestMethod.GET)
    public String  department(
            @CookieValue(value = "userVerify", required = false) String userVerify,
            Model model){
        ResultWrapper<List<Department>> res = listDepartment(userVerify);
        model.addAttribute("state", res.getState());
        model.addAttribute("list", res.getData());
        return "/department/department";
    }

    @RequestMapping(
            value = "/api/addDepartment",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //添加新的部门，需要cookie中存储有管理员权限的用户
    public ResultWrapper<Department> addDepartment(
            @CookieValue(value = "userVerify", required = false) String userVerify,
            @ModelAttribute("departmentName") String departmentName,
            @ModelAttribute("departmentParent") int departmentParent) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission != 200) {
            return new ResultWrapper<Department>(permission, null);
        }
        Department parent = departmentService.queryById(departmentParent);
        if (parent == null) {
            return new ResultWrapper<Department>(602, null);
        }
        Department department = departmentService.addDepartment(departmentName, departmentParent);
        if (department == null) {
            return new ResultWrapper<Department>(600, null);
        }
        else {
            return new ResultWrapper<Department>(200, department);
        }
    }

    @RequestMapping(
            value = "/api/deleteDepartment",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    //传入用户id，删除该id的部门，要求该部门内无员工且是三个顶层部门以外的部门，需要cookie中存储有具有管理员权限的用户
    public ResultWrapper<Department> deleteDepartment(
            @CookieValue(value = "userVerify", required = false) String userVerify,
            @ModelAttribute("departmentId") int departmentId) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission != 200) {
            return new ResultWrapper<Department>(permission, null);
        }
        List<User> userList = userService.queryByDepartment(departmentId);
        if (userList.size() != 0) {
            return new ResultWrapper<Department>(605, null);
        }
        int result = departmentService.deleteDepartment(departmentId);
        return new ResultWrapper<Department>(result, null);
    }

    @RequestMapping(
            value = "/api/listDepartment",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public ResultWrapper<List<Department>> listDepartment(
            @CookieValue(value = "userVerify", required = false) String userVerify){
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission != 200) {
            return new ResultWrapper<List<Department>>(permission, null);
        }

        List<Department> list = departmentService.queryAll();
        if(list == null){
            return new ResultWrapper<List<Department>>(ResultStateEnum.UNKNOWN_INNER_FAULT.getState(), null);
        }else{
            return new ResultWrapper<List<Department>>(ResultStateEnum.OK.getState(), list);
        }
    }
}
