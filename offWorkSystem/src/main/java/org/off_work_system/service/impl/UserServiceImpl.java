package org.off_work_system.service.impl;

import org.off_work_system.dao.DepartmentDao;
import org.off_work_system.dao.UserDao;
import org.off_work_system.entity.Department;
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

    @Autowired
    private DepartmentDao departmentDao;

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

    public boolean isAdmin(int userId) {
        User user = getUser(userId);
        return isAdmin(user);
    }

    public boolean isAdmin(User user) {
        int departmentParent = user.getDepartment().getDepartmentParent();
        if (departmentParent == -1 && user.getUserLeader() == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public int verifyCookieOfAdmin(String userVerify) {
        if (userVerify == null) {
            return 490;
        }

        System.out.println(userVerify);
        String[] userStr = userVerify.split("\\|");
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

        if (isAdmin(user)) {
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
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    public int resetPassword(int userId) {
        String password = "000000";
        User user = getUser(userId);
        user.setUserPassword(password);
        int result = userDao.updateUser(user);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    public int deleteById(int userId) {
        int result = userDao.deleteById(userId);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    public int addUser(String userUsername,
                       String userName,
                       String userSex,
                       int userAge,
                       int userDepartment,
                       int userLeader,
                       int userTimeLeft) {
        User user = User.getInstance(0,
                userUsername,
                "000000",
                userName,
                userSex,
                userAge,
                userDepartment,
                userLeader,
                userTimeLeft);
        User sameUsername = userDao.queryByUsername(userUsername);
        if (sameUsername != null) {
            return 601;
        }
        else {
            //非法输入检查
            //避免用户采取某些方法绕过了前台检查
            if (!userSex.equals("男") && !userSex.equals("女")) {
                return 602;
            }
            if (userAge <= 0) {
                return 602;
            }
            Department department = departmentDao.queryById(userDepartment);
            if (department == null) {
                return 602;
            }
            if (userLeader != 0 && userLeader != 1) {
                return 602;
            }
            if (userTimeLeft < 0) {
                return 602;
            }

            int result = userDao.addUser(user);
            if (result != 0) {
                return 200;
            }
            else {
                return 600;
            }
        }
    }

    public int isMyself(String userVerify, int userId) {
        User user = isLogin(userVerify);
        if (user == null) {
            return 490;
        }
        else if (user.getUserId() == userId) {
            return 200;
        }
        else {
            return 403;
        }
    }

    public int editMyself(int userId, String userName, String userSex, int userAge) {
        User user = userDao.queryById(userId);
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        int result = userDao.updateUser(user);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    public int changeMyPassword(int userId, String oldPassword, String newPassword) {
        User user = userDao.queryById(userId);
        if (user.getUserPassword().equals(oldPassword)) {
            user.setUserPassword(newPassword);
            int result = userDao.updateUser(user);
            if (result != 0) {
                return 200;
            }
            else {
                return 600;
            }
        }
        else {
            return 603;
        }
    }
}
