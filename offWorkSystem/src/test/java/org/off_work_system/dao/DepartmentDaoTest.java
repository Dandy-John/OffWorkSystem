package org.off_work_system.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class DepartmentDaoTest {

    @Resource
    private DepartmentDao departmentDao;

    @Test
    public void queryById() throws Exception {
        int id = 1003;
        Department department = departmentDao.queryById(id);
        System.out.println(department);
    }

    @Test
    public void queryAll() throws Exception {
        List<Department> departmentList = departmentDao.queryAll(0, 6);
        for (Department department : departmentList) {
            System.out.println(department);
        }
    }

    @Test
    public void addDepartment() throws Exception {
        Department department = Department.getInstance(1111, "testde", 1000);
        int result = departmentDao.addDepartment(department);
        System.out.println(result);
    }

    @Test
    public void deleteById() throws Exception {
        int id = 1111;
        int result = departmentDao.deleteById(id);
        System.out.println(result);
    }

    @Test
    public void updateDepartment() throws Exception {
        Department department = Department.getInstance(1111, "testupdate", 1001);
        int result = departmentDao.updateDepartment(department);
        System.out.println(result);
    }

    @Test
    public void size() throws Exception {
        int size = departmentDao.size();
        System.out.println(size);
    }

}