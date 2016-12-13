package org.off_work_system.service;

import org.off_work_system.entity.Department;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/6.
 */
public interface DepartmentService {

    List<Department> queryAll();

    Department queryById(int departmentId);

    Department addDepartment(String departmentName, int departmentParent);

    int deleteDepartment(int departmentId);
}
