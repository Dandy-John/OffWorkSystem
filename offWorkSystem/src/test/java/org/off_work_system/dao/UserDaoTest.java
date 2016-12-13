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

    /*
    单元测试
     */
    @Test
    public void queryById() throws Exception {
        int id = 1000;
        User user = userDao.queryById(id);
        System.out.println(user);
    }

    @Test
    public void queryByUsername() throws Exception {
        String username = "abc";
        User user = userDao.queryByUsername(username);
        System.out.println(user);
    }

    /*
    单元测试
     */
    @Test
    public void queryAll() throws Exception {
        List<User> userList = userDao.queryAll(0, 3);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void queryByDepartment() throws Exception {
        int userDepartment = 1000;
        List<User> userList = userDao.queryByDepartment(userDepartment);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /*
    单元测试
     */
    @Test
    public void addUser() throws Exception {
        User user = User.getInstance(1112,
                "aaa",
                "123",
                "test2",
                "男",
                12,
                1000,
                0,
                11);
        int result = userDao.addUser(user);
        System.out.println(result);
    }

    /*
    单元测试
     */
    @Test
    public void deleteById() throws Exception {
        int id = 1111;
        int result = userDao.deleteById(id);
        System.out.println(result);
    }

    /*
    单元测试
     */
    @Test
    public void updateUser() throws Exception {
        User user = User.getInstance(1111,
                "dec",
                "123321",
                "test123",
                "男",
                11,
                1001,
                0,
                22);
        int result = userDao.updateUser(user);
        System.out.println(result);
    }

    @Test
    public void tryLogin() throws Exception {
        String username = "abc";
        String password = "123";
        User user = userDao.tryLogin(username, password);
        System.out.println(user);
    }

    @Test
    public void size() throws Exception {
        int size = userDao.size();
        System.out.println(size);
    }
}