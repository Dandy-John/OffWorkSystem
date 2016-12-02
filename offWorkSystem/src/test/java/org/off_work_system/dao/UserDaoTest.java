package org.off_work_system.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.User;
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
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void queryById() throws Exception {
        int id = 1000;
        User user = userDao.queryById(id);
        System.out.println(user);
    }

    @Test
    public void queryAll() throws Exception {
        List<User> userList = userDao.queryAll(0, 3);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void addUser() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void updateUser() throws Exception {

    }

}