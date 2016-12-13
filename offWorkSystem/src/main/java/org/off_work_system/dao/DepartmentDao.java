package org.off_work_system.dao;

import org.apache.ibatis.annotations.Param;
import org.off_work_system.entity.Department;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public interface DepartmentDao {

    Department queryById(@Param("departmentId") int departmentId);

    List<Department> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int addDepartment(@Param("department") Department department);

    int getMaxId();

    int deleteById(@Param("departmentId") int departmentId);

    int updateDepartment(@Param("department") Department department);

    int size();
}
