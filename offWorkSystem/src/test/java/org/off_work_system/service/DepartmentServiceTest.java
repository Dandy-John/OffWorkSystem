package org.off_work_system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 张栋迪 on 2016/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    public void queryAll() throws Exception {
        List<Department> departmentList = departmentService.queryAll();
        for (Department department : departmentList) {
            System.out.println(department);
        }
    }

}