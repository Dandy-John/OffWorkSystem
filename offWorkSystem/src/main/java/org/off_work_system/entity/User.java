package org.off_work_system.entity;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public class User {

    //用户id
    private int userId;

    //用户名
    private String userUsername;

    //密码
    private String userPassword;

    //用户姓名
    private String userName;

    //用户性别（男、女）
    private String userSex;

    private int userAge;

    //用户所属部门id
    private int userDepartment;

    //用户是否是所在部门的领导（是：1 不是：0）
    private int userLeader;

    //用户剩余年假天数
    private int userTimeLeft;

    private int isAdmin;

    //用户所属部门的实例
    private Department department;

    public static User getInstance(int userId,
                                   String userUsername,
                                   String userPassword,
                                   String userName,
                                   String userSex,
                                   int userAge,
                                   int userDepartment,
                                   int userLeader,
                                   int userTimeLeft,
                                   int isAdmin) {
        User user = new User();
        user.setUserId(userId);
        user.setUserUsername(userUsername);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        user.setUserDepartment(userDepartment);
        user.setUserLeader(userLeader);
        user.setUserTimeLeft(userTimeLeft);
        user.setDepartment(null);
        user.setIsAdmin(isAdmin);

        return user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(int userDepartment) {
        this.userDepartment = userDepartment;
    }

    public int getUserLeader() {
        return userLeader;
    }

    public void setUserLeader(int userLeader) {
        this.userLeader = userLeader;
    }

    public int getUserTimeLeft() {
        return userTimeLeft;
    }

    public void setUserTimeLeft(int userTimeLeft) {
        this.userTimeLeft = userTimeLeft;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        String de;
        if (department == null) {
            de = "null";
        }
        else {
            de = department.toString();
        }
        return "User{" +
                "\nuserId=" + userId +
                ",\nuserUsername='" + userUsername + '\'' +
                ",\nuserPassword='" + userPassword + '\'' +
                ",\nuserName='" + userName + '\'' +
                ",\nuserSex='" + userSex + '\'' +
                ",\nuserAge=" + userAge +
                ",\nuserDepartment=" + userDepartment +
                ",\nuserLeader=" + userLeader +
                ",\nuserTimeLeft=" + userTimeLeft +
                ",\nisAdmin=" + isAdmin +
                ",\ndepartment=" + de +
                '}';
    }
}
