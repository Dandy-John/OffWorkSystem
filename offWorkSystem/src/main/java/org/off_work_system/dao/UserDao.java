package org.off_work_system.dao;

import org.apache.ibatis.annotations.Param;
import org.off_work_system.entity.User;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public interface UserDao {

    User queryById(@Param("userId") int userId);

    User queryByUsername(@Param("userUsername") String userUsername);

    List<User> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int addUser(@Param("user") User user);

    int deleteById(@Param("userId") int userId);

    int updateUser(@Param("user") User user);

    User tryLogin(@Param("userUsername") String userUsername, @Param("userPassword") String userPassword);

    int size();
}
