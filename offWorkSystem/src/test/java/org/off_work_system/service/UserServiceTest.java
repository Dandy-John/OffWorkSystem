package org.off_work_system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserList() throws Exception {
        List<User> userList = userService.getUserList();
        System.out.println(userList);
    }

    @Test
    public void queryByDepartment() throws Exception {
        int userDepartment = 1003;
        List<User> userList = userService.queryByDepartment(userDepartment);
        System.out.println("userList size: " + userList.size());
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void getUser() throws Exception {
        int userId = 1000;
        User user = userService.getUser(userId);
        System.out.println(user);
    }

    @Test
    public void checkUser() throws Exception {
        String username = "y1";
        String password = "000000";
        User user = userService.checkUser(username, password);
        System.out.println(user);
    }

    @Test
    public void addUser() throws Exception {
        int result = userService.addUser("aaaa",
                "User",
                "男",
                18,
                1001,
                0,
                12,
                0);
        System.out.println(result);
    }

    @Test
    public void deleteById() throws Exception {
        int userId = 1015;
        int result = userService.deleteById(userId);
        System.out.println(result);
    }
}