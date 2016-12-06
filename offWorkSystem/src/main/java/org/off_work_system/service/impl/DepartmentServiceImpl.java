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
}
