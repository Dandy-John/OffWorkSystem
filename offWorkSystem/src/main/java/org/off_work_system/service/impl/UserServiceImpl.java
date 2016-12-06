package org.off_work_system.service.impl;

import org.off_work_system.dao.UserDao;
import org.off_work_system.entity.User;
import org.off_work_system.exception.PermissionDeniedException;
import org.off_work_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getUserList() throws PermissionDeniedException {
        return userDao.queryAll(0, userDao.size());
    }

    public User getUser(int userId) throws PermissionDeniedException {
        return userDao.queryById(userId);
    }

    public User checkUser(String username, String password) {
        User user = userDao.tryLogin(username, password);
        return user;
    }

    public boolean userListPermission(int userId) {
        User user = getUser(userId);
        return userListPermission(user);
    }

    public boolean userListPermission(User user) {
        int departmentParent = user.getDepartment().getDepartmentParent();
        if (departmentParent == -1 && user.getUserLeader() == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public int verifyUserCookieOfListPermission(String cookieStr) {
        if (cookieStr == null) {
            return 490;
        }

        System.out.println(cookieStr);
        String[] userStr = cookieStr.split("\\|");
        for (String str : userStr) {
            System.out.println(str);
        }
        String username = userStr[0];
        String password = userStr[1];
        String timeStr = userStr[2];
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeStr));
        cal.add(Calendar.DATE, 1);

        Calendar now = Calendar.getInstance();

        //cookie过期
        if (cal.compareTo(now) < 0) {
            return 490;
        }

        User user = checkUser(username, password);
        if (user == null) {
            return 490;
        }

        if (userListPermission(user)) {
            return 200;
        }
        else {
            return 403;
        }
    }

    public User isLogin(String userVerify) {
        if (userVerify == null) {
            return null;
        }
        String[] userStr = userVerify.split("\\|");
        String username = userStr[0];
        String password = userStr[1];
        String timeStr = userStr[2];

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeStr));
        cal.add(Calendar.DATE, 1);

        Calendar now = Calendar.getInstance();

        //cookie过期
        if (cal.compareTo(now) < 0) {
            return null;
        }

        User user = checkUser(username, password);
        return user;
    }

    public int updateUser(int userId, String userName, String userSex, int userAge, int userDepartment, int userLeader, int userTimeLeft) {
        User oldUser = getUser(userId);
        User newUser = User.getInstance(userId,
                oldUser.getUserUsername(),
                oldUser.getUserPassword(),
                userName,
                userSex,
                userAge,
                userDepartment,
                userLeader,
                userTimeLeft);
        int result = userDao.updateUser(newUser);
        return result;
    }
}
