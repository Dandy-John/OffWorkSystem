package org.off_work_system.service.impl;

import org.off_work_system.dao.DepartmentDao;
import org.off_work_system.entity.Department;
import org.off_work_system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/6.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public List<Department> queryAll() {
        return departmentDao.queryAll(0, departmentDao.size());
    }

    public Department queryById(int departmentId) {
        Department department = departmentDao.queryById(departmentId);
        return department;
    }

    public Department addDepartment(String departmentName, int departmentParent) {
        int departmentId = departmentDao.getMaxId() + 1;
        Department parent = departmentDao.queryById(departmentParent);
        if (parent == null) {
            return null;
        }
        Department department = Department.getInstance(departmentId, departmentName, departmentParent);
        int result = departmentDao.addDepartment(department);
        if (result != 0) {
            return department;
        }
        else {
            return null;
        }
    }

    public int deleteDepartment(int departmentId) {
        Department department = departmentDao.queryById(departmentId);
        if (department == null) {
            return 602;
        }
        if (department.getDepartmentParent() == -1) {
            return 604;
        }
        int result = departmentDao.deleteById(departmentId);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }
}
