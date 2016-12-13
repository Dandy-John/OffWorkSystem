package org.off_work_system.web;

import org.off_work_system.dto.ResultWrapper;
import org.off_work_system.entity.Department;
import org.off_work_system.service.DepartmentService;
import org.off_work_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            value = "/api/addDepartment",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
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
    public ResultWrapper<Department> deleteDepartment(
            @CookieValue(value = "userVerify", required = false) String userVerify,
            @ModelAttribute("departmentId") int departmentId) {
        int permission = userService.verifyCookieOfAdmin(userVerify);
        if (permission != 200) {
            return new ResultWrapper<Department>(permission, null);
        }
        int result = departmentService.deleteDepartment(departmentId);
        return new ResultWrapper<Department>(result, null);
    }
}
