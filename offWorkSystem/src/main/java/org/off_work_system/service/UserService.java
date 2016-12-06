package org.off_work_system.service;

import org.off_work_system.entity.User;
import org.off_work_system.exception.PermissionDeniedException;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
public interface UserService {

    List<User> getUserList() throws PermissionDeniedException;

    User getUser(int userId) throws PermissionDeniedException;

    User checkUser(String username, String password);

    boolean userListPermission(int userId);

    int verifyUserCookieOfListPermission(String cookieStr);

    User isLogin(String userVerify);

    int updateUser(int userId, String userName, String userSex, int userAge, int userDepartment, int userLeader, int userTimeLeft);

    int resetPassword(int userId);
}
